package edu.rutmiit.demo.catgrpcenrichmentclient.publisher;

import edu.rutmiit.demo.cateventscontract.events.CatEvent;
import edu.rutmiit.demo.cateventscontract.events.EventEnvelope;
import edu.rutmiit.demo.cateventscontract.events.RoutingKeys;
import edu.rutmiit.demo.grpc.CatAnalysisResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsEventPublisher.class);
    private static final String SOURCE = "grpc-enrichment-client";
    private final RabbitTemplate rabbitTemplate;

    public AnalyticsEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishAnalytics(CatAnalysisResponse response, Long catId, String catName) {
        var enrichedEvent = new CatEvent.Enriched(
                response.getCatId(),
                catName,
                response.getActivityLevel(),
                response.getHealthStatus(),
                response.getRecommendationScore(),
                response.getEnrichmentSuggestion(),
                response.getRecommendedToys()
        );
        try {
            EventEnvelope<CatEvent> envelope = EventEnvelope.wrap(enrichedEvent, SOURCE, RoutingKeys.CAT_ENRICHED);
            rabbitTemplate.convertAndSend(RoutingKeys.EXCHANGE, RoutingKeys.CAT_ENRICHED, envelope);
            log.info("Событие отправлено: {} [catId={}]", RoutingKeys.CAT_ENRICHED, catId);
        } catch (Exception e) {
            log.error("Не удалось отправить событие: {}", e.getMessage());
        }
    }
}