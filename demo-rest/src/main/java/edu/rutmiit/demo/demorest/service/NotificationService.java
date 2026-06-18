package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.catapicontract.dto.EventType;
import edu.rutmiit.demo.catapicontract.dto.NotificationResponse;
import edu.rutmiit.demo.catapicontract.dto.PagedResponse;
import edu.rutmiit.demo.catapicontract.exception.ResourceNotFoundException;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NotificationService {

    private final InMemoryStorage storage;

    public NotificationService(InMemoryStorage storage) {
        this.storage = storage;
    }

    public void sendNotification(Long catId, String title, String body) {
        long id = storage.notificationSeq.incrementAndGet();
        NotificationResponse notif = NotificationResponse.builder()
                .id(id)
                .title(title)
                .body(body)
                .triggerEvent(mapToEventType(title))
                .sentAt(LocalDateTime.now())
                .read(false)
                .build();
        storage.notifications.put(id, notif);
    }

    public NotificationResponse findById(Long id) {
        return Optional.ofNullable(storage.notifications.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Уведомление", id));
    }

    public PagedResponse<NotificationResponse> findAll(Boolean read, LocalDateTime from, LocalDateTime to,
                                                       int page, int size) {
        Stream<NotificationResponse> stream = storage.notifications.values().stream()
                .sorted(Comparator.comparing(NotificationResponse::getSentAt).reversed());
        if (read != null) {
            stream = stream.filter(n -> n.isRead() == read);
        }
        if (from != null) {
            stream = stream.filter(n -> !n.getSentAt().isBefore(from));
        }
        if (to != null) {
            stream = stream.filter(n -> !n.getSentAt().isAfter(to));
        }
        List<NotificationResponse> all = stream.toList();
        int total = all.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);
        List<NotificationResponse> content = (fromIndex >= total) ? List.of() : all.subList(fromIndex, toIndex);
        int totalPages = (size > 0) ? (int) Math.ceil((double) total / size) : 1;
        return new PagedResponse<>(content, page, size, total, totalPages, page >= totalPages - 1);
    }

    public NotificationResponse markAsRead(Long id) {
        NotificationResponse existing = findById(id);
        NotificationResponse updated = NotificationResponse.builder()
                .id(existing.getId())
                .title(existing.getTitle())
                .body(existing.getBody())
                .triggerEvent(existing.getTriggerEvent())
                .sentAt(existing.getSentAt())
                .read(true)
                .build();
        storage.notifications.put(id, updated);
        return updated;
    }

    private EventType mapToEventType(String title) {
        if (title.contains("Миска пуста")) return EventType.BOWL_EMPTY;
        if (title.contains("Лоток использован")) return EventType.LITTER_USED;
        if (title.contains("Кот слишком долго спит")) return EventType.INACTIVITY;
        if (title.contains("Дверь открыта")) return EventType.DOOR_OPENED;
        return null;
    }
}