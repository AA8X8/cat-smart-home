package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.catapicontract.dto.ActionResponse;
import edu.rutmiit.demo.demorest.controllers.ActionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActionModelAssembler implements RepresentationModelAssembler<ActionResponse, EntityModel<ActionResponse>> {
    @Override
    public EntityModel<ActionResponse> toModel(ActionResponse action) {
        return EntityModel.of(action,
                linkTo(methodOn(ActionController.class).getActionById(action.getActionId())).withSelfRel(),
                linkTo(methodOn(ActionController.class).getAllActions(null, null, 0, 20)).withRel("actions")
        );
    }
}
