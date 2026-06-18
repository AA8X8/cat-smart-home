package edu.rutmiit.demo.booksapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "authors", itemRelation = "author")
@Schema(description = "Информация об авторе")
public class AuthorResponse extends RepresentationModel<AuthorResponse> {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String email;
    private final String bio;
    private final LocalDate birthDate;
    private final String nationality;
    private final Integer booksCount;
    private final String numberPhone;
}