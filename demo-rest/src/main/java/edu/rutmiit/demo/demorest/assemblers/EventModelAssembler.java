package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.catapicontract.dto.EventResponse;
import edu.rutmiit.demo.demorest.controllers.EventController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventModelAssembler implements RepresentationModelAssembler<EventResponse, EntityModel<EventResponse>> {
    @Override
    public EntityModel<EventResponse> toModel(EventResponse event) {
        return EntityModel.of(event,
                linkTo(methodOn(EventController.class).getEventById(event.getId())).withSelfRel(),
                linkTo(methodOn(EventController.class).getAllEvents(null, null, null, null, 0, 20)).withRel("events")
        );
    }
}
