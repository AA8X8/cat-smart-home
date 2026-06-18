package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.catapicontract.exception.ResourceNotFoundException;
import edu.rutmiit.demo.demorest.event.ActionEventPublisher;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ActionService {
    private final InMemoryStorage storage;
    private final ActionEventPublisher actionEventPublisher;

    public ActionService(InMemoryStorage storage, ActionEventPublisher actionEventPublisher) {
        this.storage = storage;
        this.actionEventPublisher = actionEventPublisher;
    }

    public ActionResponse executeAction(ActionRequest request) {
        long id = storage.actionSeq.incrementAndGet();
        ActionResponse response = ActionResponse.builder()
                .actionId(id)
                .type(request.type())
                .status(ActionStatus.SUCCESS)
                .message("Действие выполнено: " + request.type())
                .timestamp(LocalDateTime.now())
                .build();
        storage.actions.put(id, response);
        return response;
    }

    public PagedResponse<ActionResponse> findAll(Long catId, ActionRequest.ActionType type, int page, int size) {
        Stream<ActionResponse> stream = storage.actions.values().stream()
                .sorted(Comparator.comparing(ActionResponse::getTimestamp).reversed());
        if (type != null) {
            stream = stream.filter(a -> a.getType() == type);
        }
        List<ActionResponse> all = stream.toList();
        int total = all.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        List<ActionResponse> content = from >= total ? List.of() : all.subList(from, to);
        int totalPages = size > 0 ? (int) Math.ceil((double) total / size) : 1;
        return new PagedResponse<>(content, page, size, total, totalPages, page >= totalPages - 1);
    }

    public ActionResponse findById(Long id) {
        return Optional.ofNullable(storage.actions.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Действие", id));
    }

    public void deleteAction(Long id) {
        ActionResponse existing = findById(id);
        storage.actions.remove(id);
    }

    public ActionEventPublisher getActionEventPublisher() {
        return actionEventPublisher;
    }
}