package edu.rutmiit.demo.catapicontract.dto;

import edu.rutmiit.demo.catapicontract.validation.ValidVisitsHall;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Частичное обновление кота (PATCH). Передайте только те поля, которые нужно изменить.")
public record CatPatchRequest(
        @Size(max = 100, message = "Имя не может превышать 100 символов")
        String name,
        @Size(max = 100, message = "Порода не может превышать 100 символов")
        String breed,
        @Past(message = "Дата рождения должна быть в прошлом")
        LocalDate birthDate,
        @Size(max = 500, message = "Описание не может превышать 500 символов")
        String description,
        @Size(max = 50, message = "Статус активности не может превышать 50 символов")
        String activityStatus,
        @ValidVisitsHall
        Integer visitsHall
) {}