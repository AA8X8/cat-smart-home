package edu.rutmiit.demo.demorest.event;

import edu.rutmiit.demo.catapicontract.dto.EventResponse;
import edu.rutmiit.demo.cateventscontract.events.EventEnvelope;
import edu.rutmiit.demo.cateventscontract.events.RoutingKeys;
import edu.rutmiit.demo.cateventscontract.events.SensorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SensorEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(SensorEventPublisher.class);
    private static final String SOURCE = "demo-rest";
    private final RabbitTemplate rabbitTemplate;

    public SensorEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishSensorEvent(EventResponse event, String catName) {
        var sensorEvent = new SensorEvent.Occurred(
                event.getId(),
                event.getType().name(),
                event.getCatId(),
                catName,
                event.getPayload()
        );
        try {
            EventEnvelope<SensorEvent> envelope = EventEnvelope.wrap(sensorEvent, SOURCE, RoutingKeys.SENSOR_EVENT);
            rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.SENSOR_EVENT, envelope);
            log.info("Событие датчика отправлено: eventId={}, catName={}", event.getId(), catName);
        } catch (Exception e) {
            log.error("Не удалось отправить событие датчика: {}", e.getMessage());
        }
    }
}