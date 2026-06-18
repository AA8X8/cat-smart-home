package edu.rutmiit.demo.demorest.controllers;

import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.catapicontract.endpoints.EventApi;
import edu.rutmiit.demo.demorest.assemblers.EventModelAssembler;
import edu.rutmiit.demo.demorest.service.EventService;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EventController implements EventApi {

    private final EventService eventService;
    private final EventModelAssembler assembler;

    public EventController(EventService eventService, EventModelAssembler assembler) {
        this.eventService = eventService;
        this.assembler = assembler;
    }

    @Override
    public EntityModel<EventResponse> getEventById(Long id) {
        return assembler.toModel(eventService.findById(id));
    }

    @Override
    public PagedModel<EntityModel<EventResponse>> getAllEvents(EventType type, Long catId,
                                                               LocalDateTime from, LocalDateTime to,
                                                               int page, int size) {
        PagedResponse<EventResponse> paged = eventService.findAll(type, catId, from, to, page, size);

        List<EntityModel<EventResponse>> content = paged.content().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                paged.pageSize(), paged.pageNumber(), paged.totalElements());

        PagedModel<EntityModel<EventResponse>> pagedModel = PagedModel.of(content, metadata);

        // Навигационные ссылки (сохраняем фильтры)
        pagedModel.add(linkTo(methodOn(EventController.class)
                .getAllEvents(type, catId, from, to, 0, size)).withRel("first"));
        pagedModel.add(linkTo(methodOn(EventController.class)
                .getAllEvents(type, catId, from, to, paged.totalPages() - 1, size)).withRel("last"));

        if (paged.pageNumber() > 0) {
            pagedModel.add(linkTo(methodOn(EventController.class)
                    .getAllEvents(type, catId, from, to, paged.pageNumber() - 1, size)).withRel("prev"));
        }
        if (paged.pageNumber() < paged.totalPages() - 1) {
            pagedModel.add(linkTo(methodOn(EventController.class)
                    .getAllEvents(type, catId, from, to, paged.pageNumber() + 1, size)).withRel("next"));
        }

        return pagedModel;
    }

    @Override
    public ResponseEntity<EntityModel<EventResponse>> createEvent(EventRequest request) {
        EventResponse created = eventService.createEvent(request);
        EntityModel<EventResponse> model = assembler.toModel(created);
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @Override
    public EventStatistics getStatistics(LocalDateTime from, LocalDateTime to) {
        return eventService.getStatistics(from, to);
    }
}