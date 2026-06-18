package edu.rutmiit.demo.catapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Стандартный ответ об ошибке (RFC 7807)")
public record ErrorResponse(
        int status,
        String type,
        String title,
        String detail,
        String instance,
        Instant timestamp,
        List<FieldError> fieldErrors
) {
    public record FieldError(String field, Object rejectedValue, String message) {}
}