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
@Relation(collectionRelation = "notifications", itemRelation = "notification")
@Schema(description = "Уведомление, отправленное хозяину")
public class NotificationResponse extends RepresentationModel<NotificationResponse> {
    private final Long id;
    private final String title;
    private final String body;
    private final EventType triggerEvent;
    private final LocalDateTime sentAt;
    private final boolean read;
}
