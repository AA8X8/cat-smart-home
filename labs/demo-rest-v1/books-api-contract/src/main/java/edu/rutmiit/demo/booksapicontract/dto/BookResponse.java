package edu.rutmiit.demo.booksapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "books", itemRelation = "book")
@Schema(description = "Информация о книге")
public class BookResponse extends RepresentationModel<BookResponse> {

    private final Long id;
    private final String title;
    private final String isbn;
    private final AuthorResponse author;
    private final String description;
    private final String genre;
    private final Integer publishedYear;
    private final String language;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
}