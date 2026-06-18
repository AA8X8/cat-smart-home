package edu.rutmiit.demo.demorest.graphql.types;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Входной тип для создания автора.
 * Соответствует input CreateAuthorInput в GraphQL-схеме.
 */
public record CreateAuthorInputGql(
        String firstName,

        String lastName,

        String email,

        String bio,

        LocalDate birthDate,

        String numberPhone,

        String nationality
) {
}
