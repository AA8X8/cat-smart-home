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
@Relation(collectionRelation = "reviews", itemRelation = "review")
@Schema(description = "Отзыв на книгу")
public class Review extends RepresentationModel<Review> {

    @Schema(description = "Уникальный идентификатор отзыва", example = "1")
    private final Long id;

    @Schema(description = "Текст отзыва", example = "Отличная книга!")
    private final String text;

    @Schema(description = "Оценка (1–5)", example = "5")
    private final Integer rating;

    @Schema(description = "Дата и время создания отзыва")
    private final OffsetDateTime createdAt;
}