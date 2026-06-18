package edu.rutmiit.demo.demorest.event;

import edu.rutmiit.demo.catapicontract.dto.CatResponse;
import edu.rutmiit.demo.cateventscontract.events.CatEvent;
import edu.rutmiit.demo.cateventscontract.events.EventEnvelope;
import edu.rutmiit.demo.cateventscontract.events.RoutingKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(CatEventPublisher.class);
    private static final String SOURCE = "demo-rest";
    private final RabbitTemplate rabbitTemplate;

    public CatEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCreated(CatResponse cat) {
        var event = new CatEvent.Created(
                cat.getId(),
                cat.getName(),
                cat.getBreed(),
                cat.getDescription(),
                cat.getActivityStatus(),
                cat.getVisitsHall()
        );
        send(RoutingKeys.CAT_CREATED, event);
    }

    public void publishUpdated(CatResponse cat) {
        var event = new CatEvent.Updated(
                cat.getId(),
                cat.getName(),
                cat.getBreed(),
                cat.getDescription(),
                cat.getActivityStatus()
        );
        send(RoutingKeys.CAT_UPDATED, event);
    }

    public void publishDeleted(Long catId, String name) {
        var event = new CatEvent.Deleted(catId, name);
        send(RoutingKeys.CAT_DELETED, event);
    }

    private void send(String routingKey, CatEvent event) {
        try {
            EventEnvelope<CatEvent> envelope = EventEnvelope.wrap(event, SOURCE, routingKey);
            rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, routingKey, envelope);
            log.info("Событие отправлено: {} [eventId={}]", routingKey, envelope.metadata().eventId());
        } catch (Exception e) {
            log.error("Не удалось отправить событие {}: {}", routingKey, e.getMessage());
        }
    }
}