package edu.rutmiit.demo.demorest.controllers;

import edu.rutmiit.demo.catapicontract.dto.*;
import edu.rutmiit.demo.catapicontract.endpoints.CatApi;
import edu.rutmiit.demo.demorest.assemblers.CatModelAssembler;
import edu.rutmiit.demo.demorest.service.CatService;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CatController implements CatApi {

    private final CatService catService;
    private final CatModelAssembler assembler;

    public CatController(CatService catService, CatModelAssembler assembler) {
        this.catService = catService;
        this.assembler = assembler;
    }

    @Override
    public PagedModel<EntityModel<CatResponse>> getAllCats(int page, int size) {
        PagedResponse<CatResponse> paged = catService.findAll(page, size);

        List<EntityModel<CatResponse>> content = paged.content().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                paged.pageSize(), paged.pageNumber(), paged.totalElements());

        PagedModel<EntityModel<CatResponse>> pagedModel = PagedModel.of(content, metadata);

        // Добавляем навигационные ссылки
        pagedModel.add(linkTo(methodOn(CatController.class).getAllCats(0, size)).withRel("first"));
        pagedModel.add(linkTo(methodOn(CatController.class).getAllCats(paged.totalPages() - 1, size)).withRel("last"));

        if (paged.pageNumber() > 0) {
            pagedModel.add(linkTo(methodOn(CatController.class).getAllCats(paged.pageNumber() - 1, size)).withRel("prev"));
        }
        if (paged.pageNumber() < paged.totalPages() - 1) {
            pagedModel.add(linkTo(methodOn(CatController.class).getAllCats(paged.pageNumber() + 1, size)).withRel("next"));
        }

        return pagedModel;
    }

    @Override
    public EntityModel<CatResponse> getCatById(Long id) {
        return assembler.toModel(catService.findById(id));
    }

    @Override
    public ResponseEntity<EntityModel<CatResponse>> createCat(CatRequest request) {
        CatResponse created = catService.create(request);
        EntityModel<CatResponse> model = assembler.toModel(created);
        return ResponseEntity.created(model.getRequiredLink("self").toUri()).body(model);
    }

    @Override
    public EntityModel<CatResponse> updateCat(Long id, CatRequest request) {
        return assembler.toModel(catService.update(id, request));
    }

    @Override
    public EntityModel<CatResponse> patchCat(Long id, CatPatchRequest request) {
        return assembler.toModel(catService.patch(id, request));
    }

    @Override
    public void deleteCat(Long id) {
        catService.delete(id);
    }
}