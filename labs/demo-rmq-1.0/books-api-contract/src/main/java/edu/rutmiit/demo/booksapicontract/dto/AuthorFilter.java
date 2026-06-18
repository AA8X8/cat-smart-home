package edu.rutmiit.demo.booksapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false) // не включаем HATEOAS-ссылки в сравнение equals
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "authors", itemRelation = "author")
@Schema(description = "Фильтрация для автора")
@NoArgsConstructor
@AllArgsConstructor
public class AuthorFilter {
    String nationality;
    String nameSearch;
}
