package edu.rutmiit.demo.demorest.controllers;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class RootController {

    @GetMapping
    public RepresentationModel<?> getRoot() {
        RepresentationModel<?> root = new RepresentationModel<>();
        root.add(linkTo(methodOn(CatController.class).getAllCats(0, 20)).withRel("cats"));
        root.add(linkTo(methodOn(EventController.class).getAllEvents(null, null, null, null, 0, 20)).withRel("events"));
        root.add(linkTo(methodOn(ActionController.class).getAllActions(null, null, 0, 20)).withRel("actions"));
        return root;
    }
}
