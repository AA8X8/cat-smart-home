package edu.rutmiit.demo.catapicontract.dto;

import edu.rutmiit.demo.catapicontract.validation.ValidVisitsHall;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Запрос на создание или полное обновление кота")
public record CatRequest(
        @Schema(description = "Имя кота", example = "Барсик", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Имя кота не может быть пустым")
        @Size(max = 100, message = "Имя не может превышать 100 символов")
        String name,

        @Schema(description = "Порода кота", example = "Сиамская")
        @Size(max = 100, message = "Порода не может превышать 100 символов")
        String breed,

        @Schema(description = "Дата рождения кота", example = "2020-03-15")
        @Past(message = "Дата рождения должна быть в прошлом")
        LocalDate birthDate,

        @Schema(description = "Особые приметы", example = "Рыжий, пушистый")
        @Size(max = 500, message = "Описание не может превышать 500 символов")
        String description,

        @Schema(description = "Количество посещений зала", example = "5")
        @ValidVisitsHall
        Integer visitsHall
) {}