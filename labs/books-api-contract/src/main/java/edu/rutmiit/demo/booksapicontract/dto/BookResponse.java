package edu.rutmiit.demo.booksapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "books", itemRelation = "book")
@Schema(description = "Информация о книге")
public class BookResponse extends RepresentationModel<BookResponse> {

    @Schema(description = "Уникальный идентификатор книги", example = "1")
    private final Long id;

    @Schema(description = "Название книги", example = "Война и мир")
    private final String title;

    @Schema(description = "ISBN книги", example = "978-5-389-06259-8")
    private final String isbn;

    @Schema(description = "Автор книги")
    private final AuthorResponse author;

    @Schema(description = "Краткое описание книги")
    private final String description;

    @Schema(description = "Жанр книги", example = "Исторический роман")
    private final String genre;

    @Schema(description = "Год первой публикации", example = "1869")
    private final Integer publishedYear;

    @Schema(description = "Язык книги (ISO 639-1)", example = "ru")
    private final String language;

    @Schema(description = "Момент создания записи в каталоге")
    private final LocalDateTime createdAt;

    @Schema(description = "Момент последнего обновления записи")
    private final LocalDateTime updatedAt;

    // Явный конструктор для инициализации всех final полей
    public BookResponse(Long id, String title, String isbn, AuthorResponse author,
                        String description, String genre, Integer publishedYear,
                        String language, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.language = language;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}