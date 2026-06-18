package edu.rutmiit.demo.catapicontract.endpoints;

import edu.rutmiit.demo.catapicontract.config.CatHomeContractConfig;
import edu.rutmiit.demo.catapicontract.dto.CatPatchRequest;
import edu.rutmiit.demo.catapicontract.dto.CatRequest;
import edu.rutmiit.demo.catapicontract.dto.CatResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cats", description = "Управление профилями котов")
@RequestMapping(value = "/api/cats", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CatApi {

    @Operation(summary = "Список котов", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Список котов")
    @GetMapping
    PagedModel<EntityModel<CatResponse>> getAllCats(
            @Parameter(description = "Номер страницы (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "20") int size
    );

    @Operation(summary = "Получить кота по ID", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Кот найден")
    @ApiResponse(responseCode = "404", description = "Кот не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    EntityModel<CatResponse> getCatById(@PathVariable Long id);

    @Operation(summary = "Создать кота", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "201", description = "Кот создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<CatResponse>> createCat(@Valid @RequestBody CatRequest request);

    @Operation(summary = "Полное обновление кота (PUT)", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Кот обновлён")
    @ApiResponse(responseCode = "404", description = "Кот не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<CatResponse> updateCat(@PathVariable Long id, @Valid @RequestBody CatRequest request);

    @Operation(summary = "Частичное обновление кота (PATCH)", description = "Обновляет только переданные поля (JSON Merge Patch)",
            security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "200", description = "Кот обновлён")
    @ApiResponse(responseCode = "404", description = "Кот не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EntityModel<CatResponse> patchCat(@PathVariable Long id, @Valid @RequestBody CatPatchRequest request);

    @Operation(summary = "Удалить кота", security = @SecurityRequirement(name = CatHomeContractConfig.SECURITY_SCHEME_BEARER))
    @ApiResponse(responseCode = "204", description = "Кот удалён")
    @ApiResponse(responseCode = "404", description = "Кот не найден", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCat(@PathVariable Long id);
}