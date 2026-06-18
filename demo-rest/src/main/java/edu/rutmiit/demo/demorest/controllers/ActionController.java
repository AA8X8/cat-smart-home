package edu.rutmiit.demo.demorest.controllers;

import edu.rutmiit.demo.catapicontract.dto.ActionRequest;
import edu.rutmiit.demo.catapicontract.dto.ActionResponse;
import edu.rutmiit.demo.catapicontract.dto.PagedResponse;
import edu.rutmiit.demo.catapicontract.endpoints.ActionApi;
import edu.rutmiit.demo.demorest.assemblers.ActionModelAssembler;
import edu.rutmiit.demo.demorest.service.ActionService;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ActionController implements ActionApi {

    private final ActionService actionService;
    private final ActionModelAssembler assembler;

    public ActionController(ActionService actionService, ActionModelAssembler assembler) {
        this.actionService = actionService;
        this.assembler = assembler;
    }

    @Override
    public ActionResponse executeAction(ActionRequest request) {
        return actionService.executeAction(request);
    }

    @Override
    public PagedModel<EntityModel<ActionResponse>> getAllActions(Long catId, ActionRequest.ActionType type,
                                                                 int page, int size) {
        PagedResponse<ActionResponse> paged = actionService.findAll(catId, type, page, size);

        List<EntityModel<ActionResponse>> content = paged.content().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                paged.pageSize(), paged.pageNumber(), paged.totalElements());

        PagedModel<EntityModel<ActionResponse>> pagedModel = PagedModel.of(content, metadata);

        // Навигационные ссылки
        pagedModel.add(linkTo(methodOn(ActionController.class)
                .getAllActions(catId, type, 0, size)).withRel("first"));
        pagedModel.add(linkTo(methodOn(ActionController.class)
                .getAllActions(catId, type, paged.totalPages() - 1, size)).withRel("last"));

        if (paged.pageNumber() > 0) {
            pagedModel.add(linkTo(methodOn(ActionController.class)
                    .getAllActions(catId, type, paged.pageNumber() - 1, size)).withRel("prev"));
        }
        if (paged.pageNumber() < paged.totalPages() - 1) {
            pagedModel.add(linkTo(methodOn(ActionController.class)
                    .getAllActions(catId, type, paged.pageNumber() + 1, size)).withRel("next"));
        }

        return pagedModel;
    }

    @Override
    public EntityModel<ActionResponse> getActionById(Long id) {
        return assembler.toModel(actionService.findById(id));
    }
}
