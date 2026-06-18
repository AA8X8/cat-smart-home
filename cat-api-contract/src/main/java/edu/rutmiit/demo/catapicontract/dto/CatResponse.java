package edu.rutmiit.demo.catapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "cats", itemRelation = "cat")
@Schema(description = "Информация о коте")
public class CatResponse extends RepresentationModel<CatResponse> {
    private final Long id;
    private final String name;
    private final String breed;
    private final LocalDate birthDate;
    private final String description;
    private final String activityStatus;
    private final LocalDateTime lastEventTime;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    @Schema(description = "Количество посещений зала", example = "5")
    private final Integer visitsHall;
}