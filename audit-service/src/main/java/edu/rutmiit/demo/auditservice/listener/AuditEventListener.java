package edu.rutmiit.demo.auditservice.listener;

import edu.rutmiit.demo.auditservice.model.AuditEntry;
import edu.rutmiit.demo.auditservice.storage.AuditStorage;
import edu.rutmiit.demo.cateventscontract.events.ActionEvent;
import edu.rutmiit.demo.cateventscontract.events.CatEvent;
import edu.rutmiit.demo.cateventscontract.events.EventMetadata;
import edu.rutmiit.demo.cateventscontract.events.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

@Component
public class AuditEventListener {
    private static final Logger log = LoggerFactory.getLogger(AuditEventListener.class);
    private final AuditStorage auditStorage;
    private final ObjectMapper objectMapper;

    public AuditEventListener(AuditStorage auditStorage, ObjectMapper objectMapper) {
        this.auditStorage = auditStorage;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "q.audit.cat-events", messageConverter = "")
    public void handleEvent(Message message) {
        try {
            byte[] body = message.getBody();
            JsonNode root = objectMapper.readTree(body);

            JsonNode metaNode = root.get("metadata");
            EventMetadata metadata = objectMapper.treeToValue(metaNode, EventMetadata.class);

            if (auditStorage.isDuplicate(metadata.eventId())) {
                log.warn("Дубликат события пропущен: eventId={}", metadata.eventId());
                return;
            }

            JsonNode payloadNode = root.get("payload");
            String description = buildDescription(metadata.eventType(), payloadNode);

            AuditEntry entry = auditStorage.save(new AuditEntry(
                    0,
                    metadata.eventId(),
                    metadata.eventType(),
                    metadata.source(),
                    metadata.timestamp(),
                    Instant.now(),
                    description
            ));

            log.info("[AUDIT #{}] {} | {}", entry.sequenceNumber(), metadata.eventType(), description);

        } catch (Exception e) {
            log.error("Ошибка обработки события: {}", e.getMessage(), e);
            throw new RuntimeException("Не удалось обработать событие", e);
        }
    }

    private String buildDescription(String eventType, JsonNode payloadNode) throws Exception {
        return switch (eventType) {
            case "cat.created" -> {
                CatEvent.Created e = objectMapper.treeToValue(payloadNode, CatEvent.Created.class);
                yield String.format("Кот создан: id=%d, имя=%s, порода=%s",
                        e.catId(), e.name(), e.breed() != null ? e.breed() : "не указана");
            }
            case "cat.updated" -> {
                CatEvent.Updated e = objectMapper.treeToValue(payloadNode, CatEvent.Updated.class);
                yield String.format("Кот обновлён: id=%d, имя=%s, статус=%s",
                        e.catId(), e.name(), e.activityStatus());
            }
            case "cat.deleted" -> {
                CatEvent.Deleted e = objectMapper.treeToValue(payloadNode, CatEvent.Deleted.class);
                yield String.format("Кот удалён: id=%d, имя=%s", e.catId(), e.name());
            }
            case "sensor.event" -> {
                SensorEvent.Occurred e = objectMapper.treeToValue(payloadNode, SensorEvent.Occurred.class);
                yield String.format("Событие датчика: id=%d, тип=%s, кот=%d",
                        e.eventId(), e.type(), e.catId() != null ? e.catId() : 0);
            }
            case "action.executed" -> {
                ActionEvent.Executed e = objectMapper.treeToValue(payloadNode, ActionEvent.Executed.class);
                yield String.format("Действие выполнено: id=%d, тип=%s, кот=%d, статус=%s",
                        e.actionId(), e.type(), e.catId() != null ? e.catId() : 0, e.status());
            }
            case "cat.enriched" -> {
                CatEvent.Enriched e = objectMapper.treeToValue(payloadNode, CatEvent.Enriched.class);
                yield String.format("Кот обогащён: id=%d, имя=%s, уровень активности=%s, здоровье=%s, рекомендация=%s",
                        e.catId(), e.name(), e.activityLevel(), e.healthStatus(), e.enrichmentSuggestion());
            }
            default -> "Неизвестное событие: " + eventType;
        };
    }
}