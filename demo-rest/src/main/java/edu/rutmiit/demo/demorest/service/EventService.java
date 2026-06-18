package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.catapicontract.endpoints.EventApi;
import edu.rutmiit.demo.catapicontract.exception.ResourceNotFoundException;
import edu.rutmiit.demo.demorest.event.ActionEventPublisher;
import edu.rutmiit.demo.demorest.event.SensorEventPublisher;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EventService {
    private final InMemoryStorage storage;
    private final ActionService actionService;
    private final NotificationService notificationService;
    private final SensorEventPublisher sensorEventPublisher;
    private final CatService catService;

    public EventService(InMemoryStorage storage,
                        ActionService actionService,
                        NotificationService notificationService,
                        SensorEventPublisher sensorEventPublisher,
                        CatService catService) {
        this.storage = storage;
        this.actionService = actionService;
        this.notificationService = notificationService;
        this.sensorEventPublisher = sensorEventPublisher;
        this.catService = catService;
    }

    public PagedResponse<EventResponse> findAll(EventType type, Long catId, LocalDateTime from, LocalDateTime to, int page, int size) {
        Stream<EventResponse> stream = storage.events.values().stream()
                .sorted(Comparator.comparing(EventResponse::getTimestamp).reversed());
        if (type != null) stream = stream.filter(e -> e.getType() == type);
        if (catId != null) stream = stream.filter(e -> catId.equals(e.getCatId()));
        if (from != null) stream = stream.filter(e -> !e.getTimestamp().isBefore(from));
        if (to != null) stream = stream.filter(e -> !e.getTimestamp().isAfter(to));
        List<EventResponse> all = stream.toList();
        int total = all.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);
        List<EventResponse> content = fromIndex >= total ? List.of() : all.subList(fromIndex, toIndex);
        int totalPages = size > 0 ? (int) Math.ceil((double) total / size) : 1;
        return new PagedResponse<>(content, page, size, total, totalPages, page >= totalPages - 1);
    }

    public EventResponse findById(Long id) {
        return Optional.ofNullable(storage.events.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Событие", id));
    }

    public EventResponse createEvent(EventRequest request) {
        long id = storage.eventSeq.incrementAndGet();
        EventResponse event = EventResponse.builder()
                .id(id)
                .type(request.type())
                .catId(request.catId())
                .timestamp(LocalDateTime.now())
                .payload(request.payload())
                .processed(false)
                .build();
        storage.events.put(id, event);

        // Отмечаем как обработанное
        EventResponse processedEvent = EventResponse.builder()
                .id(id)
                .type(event.getType())
                .catId(event.getCatId())
                .timestamp(event.getTimestamp())
                .payload(event.getPayload())
                .processed(true)
                .build();
        storage.events.put(id, processedEvent);

        // Получаем имя кота
        String catName = null;
        if (event.getCatId() != null) {
            try {
                CatResponse cat = catService.findById(event.getCatId());
                catName = cat.getName();
            } catch (Exception ignored) {}
        }

        // 1. Сначала публикуем событие датчика (с именем)
        sensorEventPublisher.publishSensorEvent(processedEvent, catName);

        // 2. Выполняем автоматические реакции
        processEvent(processedEvent, catName);

        return processedEvent;
    }

    private void processEvent(EventResponse event, String catName) {
        Long catId = event.getCatId();
        if (catId == null) return;

        ActionEventPublisher actionPublisher = actionService.getActionEventPublisher();

        switch (event.getType()) {
            case BOWL_EMPTY:
                ActionResponse fillAction = actionService.executeAction(
                        new ActionRequest(ActionRequest.ActionType.FILL_BOWL, catId, Map.of())
                );
                catService.incrementVisitsHall(catId);
                actionPublisher.publishActionExecuted(fillAction, catId, catName);
                notificationService.sendNotification(catId, "Миска пуста", "Наполняю миску кормом.");
                break;

            case LITTER_USED:
                ActionResponse cleanAction = actionService.executeAction(
                        new ActionRequest(ActionRequest.ActionType.CLEAN_LITTER, catId, Map.of())
                );
                catService.incrementVisitsHall(catId);
                actionPublisher.publishActionExecuted(cleanAction, catId, catName);
                notificationService.sendNotification(catId, "Лоток использован", "Запущена очистка лотка.");
                break;

            case INACTIVITY:
                ActionResponse toyAction = actionService.executeAction(
                        new ActionRequest(ActionRequest.ActionType.ACTIVATE_TOY, catId, Map.of())
                );
                actionPublisher.publishActionExecuted(toyAction, catId, catName);
                notificationService.sendNotification(catId, "Кот слишком долго спит", "Включаю игрушку-дразнилку.");
                break;

            case DOOR_OPENED:
                notificationService.sendNotification(catId, "Дверь открыта", "Кто-то вышел на улицу.");
                break;

            default:
                // Остальные события (BOWL_FILLED, LITTER_CLEANED, TOY_ACTIVATED, NOTIFICATION_SENT) – не требуют действий
                break;
        }
    }

    public EventApi.EventStatistics getStatistics(LocalDateTime from, LocalDateTime to) {
        Stream<EventResponse> stream = storage.events.values().stream()
                .filter(e -> !e.getTimestamp().isBefore(from) && !e.getTimestamp().isAfter(to));
        List<EventResponse> events = stream.toList();
        long total = events.size();
        long bowlEmpty = events.stream().filter(e -> e.getType() == EventType.BOWL_EMPTY).count();
        long litterUsed = events.stream().filter(e -> e.getType() == EventType.LITTER_USED).count();
        long inactivity = events.stream().filter(e -> e.getType() == EventType.INACTIVITY).count();
        long doorOpened = events.stream().filter(e -> e.getType() == EventType.DOOR_OPENED).count();
        long notificationsSent = events.stream().filter(e -> e.getType() == EventType.NOTIFICATION_SENT).count();
        return new EventApi.EventStatistics(total, bowlEmpty, litterUsed, inactivity, doorOpened, notificationsSent);
    }
}