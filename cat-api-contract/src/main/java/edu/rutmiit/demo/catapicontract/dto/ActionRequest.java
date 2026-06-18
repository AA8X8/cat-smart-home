package edu.rutmiit.demo.catapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на выполнение действия (ручное управление)")
public record ActionRequest(
        @Schema(description = "Тип действия", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Тип действия обязателен")
        ActionType type,

        @Schema(description = "Идентификатор кота, для которого выполняется действие", example = "1")
        Long catId,

        @Schema(description = "Дополнительные параметры (например, количество порций корма)")
        Object parameters
) {
    public enum ActionType {
        FILL_BOWL,
        CLEAN_LITTER,
        ACTIVATE_TOY,
        SEND_NOTIFICATION
    }
}