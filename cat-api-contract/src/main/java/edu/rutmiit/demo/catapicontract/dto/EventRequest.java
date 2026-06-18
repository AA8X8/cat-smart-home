package edu.rutmiit.demo.catapicontract.dto;

import edu.rutmiit.demo.catapicontract.validation.ValidEventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@Schema(description = "Запрос на создание события (от датчика или для теста)")
public record EventRequest(
        @Schema(description = "Тип события", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Тип события обязателен")
        @ValidEventType
        EventType type,

        @Schema(description = "Идентификатор кота", example = "1")
        Long catId,

        @Schema(description = "Дополнительные данные события")
        Map<String, Object> payload
) {}