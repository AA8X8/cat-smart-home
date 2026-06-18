package edu.rutmiit.demo.catapicontract.endpoints;

import edu.rutmiit.demo.catapicontract.config.CatHomeContractConfig;
import edu.rutmiit.demo.catapicontract.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Events", description = "События от датчиков и системы")
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EventApi {

    @Operation(summary = "Получить событие по ID", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Событие найдено")
    @ApiResponse(responseCode = "404", description = "Событие не найдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    EntityModel<EventResponse> getEventById(@PathVariable Long id);

    @Operation(summary = "Список событий с фильтрацией", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Список событий")
    @GetMapping
    PagedModel<EntityModel<EventResponse>> getAllEvents(
            @Parameter(description = "Фильтр по типу события") @RequestParam(required = false) EventType type,
            @Parameter(description = "Фильтр по ID кота") @RequestParam(required = false) Long catId,
            @Parameter(description = "Начало периода") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @Parameter(description = "Конец периода") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @Operation(summary = "Создать событие (для тестирования)", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "201", description = "Событие создано")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<EventResponse>> createEvent(@Valid @RequestBody EventRequest request);

    @Operation(summary = "Статистика событий за период", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @GetMapping("/stats")
    EventStatistics getStatistics(
            @Parameter(description = "Начало периода (обязательно)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @Parameter(description = "Конец периода (обязательно)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    );

    @Schema(description = "Статистика событий за период")
    record EventStatistics(
            long totalEvents,
            long bowlEmptyCount,
            long litterUsedCount,
            long inactivityCount,
            long doorOpenedCount,
            long notificationsSent
    ) {}
}