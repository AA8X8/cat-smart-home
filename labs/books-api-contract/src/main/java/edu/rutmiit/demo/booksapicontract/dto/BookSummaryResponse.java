package edu.rutmiit.demo.booksapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "books", itemRelation = "book")
@Schema(description = "Укороченная информация ответа для книги")
public class BookSummaryResponse extends RepresentationModel<BookSummaryResponse> {
    @Schema(description = "ID книги", example = "1")
    private final Long id;
    @Schema(description = "Название книги", example = "Война и мир")
    private final String title;
    @Schema(description = "ISBN книги", example = "978-5-389-06259-8")
    private final String isbn;

    // Явный конструктор для инициализации всех final полей
    public BookSummaryResponse(Long id, String title, String isbn) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
    }
}