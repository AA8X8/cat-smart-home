package edu.rutmiit.demo.notificationservice.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.rutmiit.demo.cateventscontract.events.*;
import edu.rutmiit.demo.notificationservice.dto.NotificationMessage;
import edu.rutmiit.demo.notificationservice.handler.NotificationWebSocketHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationListener {

    private final ObjectMapper objectMapper;
    private final NotificationWebSocketHandler webSocketHandler;
    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    public NotificationListener(ObjectMapper objectMapper,
                                NotificationWebSocketHandler webSocketHandler) {
        this.objectMapper = objectMapper;
        this.webSocketHandler = webSocketHandler;
    }

    @RabbitListener(queues = "q.notifications.cat-events", messageConverter = "")
    public void handleEvent(Message message) {
        try {
            byte[] body = message.getBody();
            JsonNode root = objectMapper.readTree(body);

            JsonNode metaNode = root.get("metadata");
            EventMetadata metadata = objectMapper.treeToValue(metaNode, EventMetadata.class);

            if (!processedEventIds.add(metadata.eventId())) {
                return;
            }

            JsonNode payloadNode = root.get("payload");
            String title = buildTitle(metadata.eventType());
            String description = buildDescription(metadata.eventType(), payloadNode);

            NotificationMessage notification = new NotificationMessage(
                    title,
                    description,
                    metadata.eventType(),
                    Instant.now().toEpochMilli()
            );

            String json = objectMapper.writeValueAsString(notification);
            webSocketHandler.broadcast(json);

            System.out.println("📨 Уведомление отправлено: " + title);

        } catch (Exception e) {
            System.err.println("❌ Ошибка обработки события: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String buildTitle(String eventType) {
        return switch (eventType) {
            case "cat.created" -> "🐱 Новый кот";
            case "cat.updated" -> "📝 Кот обновлён";
            case "cat.deleted" -> "🗑️ Кот удалён";
            case "cat.enriched" -> "📊 Кот обогащён аналитикой";
            case "sensor.event" -> "📡 Событие датчика";
            case "action.executed" -> "⚡ Действие выполнено";
            default -> "📨 Новое событие";
        };
    }

    private String buildDescription(String eventType, JsonNode payload) throws Exception {
        return switch (eventType) {
            case "cat.created" -> {
                CatEvent.Created e = objectMapper.treeToValue(payload, CatEvent.Created.class);
                yield String.format("Кот «%s» (порода: %s, ID: %d)",
                        e.name(), e.breed() != null ? e.breed() : "не указана", e.catId());
            }
            case "cat.updated" -> {
                CatEvent.Updated e = objectMapper.treeToValue(payload, CatEvent.Updated.class);
                yield String.format("Кот «%s» (ID: %d) обновлён (статус: %s)",
                        e.name(), e.catId(), e.activityStatus());
            }
            case "cat.deleted" -> {
                CatEvent.Deleted e = objectMapper.treeToValue(payload, CatEvent.Deleted.class);
                yield String.format("Кот «%s» (ID: %d) удалён", e.name(), e.catId());
            }
            case "cat.enriched" -> {
                CatEvent.Enriched e = objectMapper.treeToValue(payload, CatEvent.Enriched.class);
                String activityLevelRu = translateActivityLevel(e.activityLevel());
                String healthStatusRu = translateHealthStatus(e.healthStatus());
                String score = formatScore(e.recommendationScore());
                yield String.format("Кот «%s» (ID: %d) | уровень активности: %s, здоровье: %s, рейтинг: %s",
                        e.name(), e.catId(), activityLevelRu, healthStatusRu, score);
            }
            case "sensor.event" -> {
                SensorEvent.Occurred e = objectMapper.treeToValue(payload, SensorEvent.Occurred.class);
                String catDisplay = e.catName() != null ? e.catName() : "Кот ID " + e.catId();
                yield String.format("%s | %s", catDisplay, translateEventType(e.type()));
            }
            case "action.executed" -> {
                ActionEvent.Executed e = objectMapper.treeToValue(payload, ActionEvent.Executed.class);
                String catDisplay = e.catName() != null ? e.catName() : "Кот ID " + e.catId();
                yield String.format("%s | %s (статус: %s)", catDisplay, translateActionType(e.type()), e.status());
            }
            default -> "Неизвестное событие: " + eventType;
        };
    }

    // ========== ПЕРЕВОДЫ ==========

    private String translateEventType(String type) {
        return switch (type) {
            case "BOWL_EMPTY" -> "Миска пуста";
            case "LITTER_USED" -> "Лоток использован";
            case "INACTIVITY" -> "Кот неактивен";
            case "DOOR_OPENED" -> "Дверь открыта";
            case "BOWL_FILLED" -> "Миска наполнена";
            case "LITTER_CLEANED" -> "Лоток очищен";
            case "TOY_ACTIVATED" -> "Игрушка активирована";
            case "NOTIFICATION_SENT" -> "Уведомление отправлено";
            default -> type;
        };
    }

    private String translateActionType(String type) {
        return switch (type) {
            case "FILL_BOWL" -> "Наполнить миску";
            case "CLEAN_LITTER" -> "Очистить лоток";
            case "ACTIVATE_TOY" -> "Активировать игрушку";
            case "SEND_NOTIFICATION" -> "Отправить уведомление";
            default -> type;
        };
    }

    private String translateActivityLevel(String level) {
        return switch (level) {
            case "HIGH" -> "высокая активность";
            case "MEDIUM" -> "средняя активность";
            case "LOW" -> "низкая активность";
            default -> level;
        };
    }

    private String translateHealthStatus(String status) {
        return switch (status) {
            case "GOOD" -> "хорошее здоровье";
            case "NEEDS_ATTENTION" -> "требуется внимание";
            case "CRITICAL" -> "критическое состояние";
            default -> status;
        };
    }

    private String formatScore(double score) {
        if (score >= 8.0) return String.format("%.1f ⭐ (отлично)", score);
        if (score >= 5.0) return String.format("%.1f ⭐ (хорошо)", score);
        return String.format("%.1f ⭐ (требуется внимание)", score);
    }
}