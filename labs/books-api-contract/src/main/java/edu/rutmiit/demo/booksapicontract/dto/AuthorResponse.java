package edu.rutmiit.demo.booksapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "authors", itemRelation = "author")
@Schema(description = "Информация об авторе")
public class AuthorResponse extends RepresentationModel<AuthorResponse> {

    @Schema(description = "Уникальный идентификатор автора", example = "1")
    private final Long id;

    @Schema(description = "Имя автора", example = "Лев")
    private final String firstName;

    @Schema(description = "Фамилия автора", example = "Толстой")
    private final String lastName;

    @Schema(description = "Полное имя (firstName + lastName)", example = "Лев Толстой")
    private final String fullName;

    @Schema(description = "Email автора", example = "tolstoy@example.com")
    private final String email;

    @Schema(description = "Краткая биография автора")
    private final String bio;

    @Schema(description = "Дата рождения автора", example = "1828-09-09")
    private final LocalDate birthDate;

    @Schema(description = "Национальность автора", example = "Русский")
    private final String nationality;

    @Schema(description = "Общее количество книг автора в каталоге", example = "3")
    private final Integer booksCount;

    @Schema(description = "Номер телефона", example = "+79262533596")
    private final String numberPhone;

    // Явный конструктор для инициализации всех final полей
    public AuthorResponse(Long id, String firstName, String lastName, String fullName,
                          String email, String bio, LocalDate birthDate, String nationality,
                          Integer booksCount, String numberPhone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.booksCount = booksCount;
        this.numberPhone = numberPhone;
    }
}