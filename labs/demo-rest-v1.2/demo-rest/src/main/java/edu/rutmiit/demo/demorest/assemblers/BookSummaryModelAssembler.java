package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.booksapicontract.dto.BookResponse;
import edu.rutmiit.demo.booksapicontract.dto.BookSummaryResponse;
import edu.rutmiit.demo.demorest.controllers.BookController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookSummaryModelAssembler implements RepresentationModelAssembler<BookSummaryResponse, EntityModel<BookSummaryResponse>> {
    @Override
    public EntityModel<BookSummaryResponse> toModel(BookSummaryResponse book) {
        EntityModel<BookSummaryResponse> model = EntityModel.of(book,
                linkTo(methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks(null, null, null, null, 0, 20)).withRel("collection"),
                linkTo(methodOn(BookController.class).getAllBooksSummary()).withRel("summary")
        );
        return model;
    }
}
