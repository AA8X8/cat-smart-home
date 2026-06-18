package edu.rutmiit.demo.demorest.event;

import edu.rutmiit.demo.catapicontract.dto.ActionResponse;
import edu.rutmiit.demo.cateventscontract.events.ActionEvent;
import edu.rutmiit.demo.cateventscontract.events.EventEnvelope;
import edu.rutmiit.demo.cateventscontract.events.RoutingKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActionEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(ActionEventPublisher.class);
    private static final String SOURCE = "demo-rest";
    private final RabbitTemplate rabbitTemplate;

    public ActionEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishActionExecuted(ActionResponse action, Long catId, String catName) {
        var actionEvent = new ActionEvent.Executed(
                action.getActionId(),
                action.getType().name(),
                catId,
                catName,
                action.getStatus().name(),
                action.getMessage()
        );
        try {
            EventEnvelope<ActionEvent> envelope = EventEnvelope.wrap(actionEvent, SOURCE, RoutingKeys.ACTION_EXECUTED);
            rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.ACTION_EXECUTED, envelope);
            log.info("Событие действия отправлено: actionId={}, catName={}", action.getActionId(), catName);
        } catch (Exception e) {
            log.error("Не удалось отправить событие действия: {}", e.getMessage());
        }
    }
}