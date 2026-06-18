package edu.rutmiit.demo.catapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "actions", itemRelation = "action")
@Schema(description = "Результат выполнения действия")
public class ActionResponse extends RepresentationModel<ActionResponse> {
    private final Long actionId;
    private final ActionRequest.ActionType type;
    private final ActionStatus status;
    private final String message;
    private final LocalDateTime timestamp;
}