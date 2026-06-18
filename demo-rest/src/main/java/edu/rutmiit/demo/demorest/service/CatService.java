package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.catapicontract.exception.ResourceNotFoundException;
import edu.rutmiit.demo.demorest.event.CatEventPublisher;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final InMemoryStorage storage;
    private final CatEventPublisher eventPublisher;

    public CatService(InMemoryStorage storage, CatEventPublisher eventPublisher) {
        this.storage = storage;
        this.eventPublisher = eventPublisher;
    }

    public PagedResponse<CatResponse> findAll(int page, int size) {
        List<CatResponse> all = storage.cats.values().stream()
                .sorted(Comparator.comparing(CatResponse::getId))
                .toList();
        int total = all.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        List<CatResponse> content = from >= total ? List.of() : all.subList(from, to);
        int totalPages = size > 0 ? (int) Math.ceil((double) total / size) : 1;
        return new PagedResponse<>(content, page, size, total, totalPages, page >= totalPages - 1);
    }

    public CatResponse findById(Long id) {
        return Optional.ofNullable(storage.cats.get(id))
                .orElseThrow(() -> new ResourceNotFoundException("Кот", id));
    }

    public CatResponse create(CatRequest request) {
        long id = storage.catSeq.incrementAndGet();
        Integer visitsHall = request.visitsHall() != null ? request.visitsHall() : 0;
        CatResponse cat = CatResponse.builder()
                .id(id)
                .name(request.name())
                .breed(request.breed())
                .birthDate(request.birthDate())
                .description(request.description())
                .activityStatus("активен")
                .createdAt(LocalDateTime.now())
                .visitsHall(visitsHall)
                .build();
        storage.cats.put(id, cat);
        eventPublisher.publishCreated(cat);
        return cat;
    }

    public CatResponse update(Long id, CatRequest request) {
        CatResponse existing = findById(id);
        Integer visitsHall = request.visitsHall() != null ? request.visitsHall() : existing.getVisitsHall();
        CatResponse updated = CatResponse.builder()
                .id(id)
                .name(request.name())
                .breed(request.breed())
                .birthDate(request.birthDate())
                .description(request.description())
                .activityStatus(existing.getActivityStatus())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .visitsHall(visitsHall)
                .build();
        storage.cats.put(id, updated);
        eventPublisher.publishUpdated(updated);
        return updated;
    }

    public CatResponse patch(Long id, CatPatchRequest request) {
        CatResponse existing = findById(id);
        Integer visitsHall = request.visitsHall() != null ? request.visitsHall() : existing.getVisitsHall();
        CatResponse patched = CatResponse.builder()
                .id(id)
                .name(request.name() != null ? request.name() : existing.getName())
                .breed(request.breed() != null ? request.breed() : existing.getBreed())
                .birthDate(request.birthDate() != null ? request.birthDate() : existing.getBirthDate())
                .description(request.description() != null ? request.description() : existing.getDescription())
                .activityStatus(request.activityStatus() != null ? request.activityStatus() : existing.getActivityStatus())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .visitsHall(visitsHall)
                .build();
        storage.cats.put(id, patched);
        eventPublisher.publishUpdated(patched);
        return patched;
    }

    public void delete(Long id) {
        CatResponse existing = findById(id);
        storage.cats.remove(id);
        eventPublisher.publishDeleted(id, existing.getName());
    }

    public void incrementVisitsHall(Long catId) {
        CatResponse existing = findById(catId);
        CatResponse updated = CatResponse.builder()
                .id(existing.getId())
                .name(existing.getName())
                .breed(existing.getBreed())
                .birthDate(existing.getBirthDate())
                .description(existing.getDescription())
                .activityStatus(existing.getActivityStatus())
                .createdAt(existing.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .visitsHall((existing.getVisitsHall() != null ? existing.getVisitsHall() : 0) + 1)
                .build();
        storage.cats.put(catId, updated);
        eventPublisher.publishUpdated(updated);
    }
}