package edu.rutmiit.demo.catapicontract.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "events", itemRelation = "event")
@Schema(description = "Информация о событии")
public class EventResponse extends RepresentationModel<EventResponse> {
    private final Long id;
    private final EventType type;
    private final Long catId;
    private final LocalDateTime timestamp;
    private final Map<String, Object> payload;
    private final boolean processed;
}
