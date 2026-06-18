package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.catapicontract.dto.CatResponse;
import edu.rutmiit.demo.demorest.controllers.CatController;
import edu.rutmiit.demo.demorest.controllers.EventController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CatModelAssembler implements RepresentationModelAssembler<CatResponse, EntityModel<CatResponse>> {
    @Override
    public EntityModel<CatResponse> toModel(CatResponse cat) {
        return EntityModel.of(cat,
                linkTo(methodOn(CatController.class).getCatById(cat.getId())).withSelfRel(),
                linkTo(methodOn(CatController.class).getAllCats(0, 20)).withRel("cats"),
                linkTo(methodOn(EventController.class).getAllEvents(null, cat.getId(), null, null, 0, 20)).withRel("events")
        );
    }
}
