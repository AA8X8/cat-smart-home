package edu.rutmiit.demo.catapicontract.endpoints;

import edu.rutmiit.demo.catapicontract.config.CatHomeContractConfig;
import edu.rutmiit.demo.catapicontract.dto.ActionRequest;
import edu.rutmiit.demo.catapicontract.dto.ActionResponse;
import edu.rutmiit.demo.catapicontract.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Actions", description = "Ручное управление действиями и история действий")
@RequestMapping(value = "/api/actions", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ActionApi {

    @Operation(summary = "Выполнить действие (наполнить миску, очистить лоток и т.д.)",
            security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Действие выполнено")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Кот не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ActionResponse executeAction(@Valid @RequestBody ActionRequest request);

    @Operation(summary = "История выполненных действий", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Список действий")
    @GetMapping
    PagedModel<EntityModel<ActionResponse>> getAllActions(
            @Parameter(description = "Фильтр по ID кота") @RequestParam(required = false) Long catId,
            @Parameter(description = "Фильтр по типу действия") @RequestParam(required = false) ActionRequest.ActionType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @Operation(summary = "Получить действие по ID", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Действие найдено")
    @ApiResponse(responseCode = "404", description = "Действие не найдено", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    EntityModel<ActionResponse> getActionById(@PathVariable Long id);
}