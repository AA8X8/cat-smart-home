package edu.rutmiit.demo.catgrpcenrichmentclient.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.rutmiit.demo.cateventscontract.events.CatEvent;
import edu.rutmiit.demo.cateventscontract.events.EventMetadata;
import edu.rutmiit.demo.grpc.AnalyzeCatRequest;
import edu.rutmiit.demo.grpc.CatAnalysisResponse;
import edu.rutmiit.demo.grpc.CatAnalyticsGrpc;
import edu.rutmiit.demo.catgrpcenrichmentclient.publisher.AnalyticsEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CatCreatedListener {
    private static final Logger log = LoggerFactory.getLogger(CatCreatedListener.class);
    private final CatAnalyticsGrpc.CatAnalyticsBlockingStub analyticsStub;
    private final AnalyticsEventPublisher analyticsPublisher;
    private final ObjectMapper objectMapper;

    public CatCreatedListener(CatAnalyticsGrpc.CatAnalyticsBlockingStub stub,
                              AnalyticsEventPublisher publisher,
                              ObjectMapper mapper) {
        this.analyticsStub = stub;
        this.analyticsPublisher = publisher;
        this.objectMapper = mapper;
    }

    @RabbitListener(queues = "q.enrichment.cat-created", messageConverter = "")
    public void handleCatCreated(Message message) {
        try {
            JsonNode root = objectMapper.readTree(message.getBody());
            JsonNode metaNode = root.get("metadata");
            EventMetadata metadata = objectMapper.treeToValue(metaNode, EventMetadata.class);
            JsonNode payloadNode = root.get("payload");
            CatEvent.Created event = objectMapper.treeToValue(payloadNode, CatEvent.Created.class);

            log.info("Получено событие cat.created: catId={}, name={}, visitsHall={} [eventId={}]",
                    event.catId(), event.name(), event.visitsHall(), metadata.eventId());

            AnalyzeCatRequest request = AnalyzeCatRequest.newBuilder()
                    .setCatId(event.catId())
                    .setName(event.name())
                    .setBreed(event.breed() != null ? event.breed() : "")
                    .setVisitsHall(event.visitsHall() != null ? event.visitsHall() : 0)
                    .setActivityHours(0.0)
                    .setEventsCount(0)
                    .setActivityStatus(event.activityStatus() != null ? event.activityStatus() : "")
                    .build();

            log.info("Вызов gRPC: CatAnalytics.AnalyzeCat(catId={}, visitsHall={})", event.catId(), request.getVisitsHall());
            CatAnalysisResponse response = analyticsStub.analyzeCat(request);

            log.info("gRPC ответ получен: catId={}, уровень={}, здоровье={}, балл={}",
                    response.getCatId(), response.getActivityLevel(),
                    response.getHealthStatus(), response.getRecommendationScore());

            analyticsPublisher.publishAnalytics(response, event.catId(), event.name());

        } catch (Exception e) {
            log.error("Ошибка обработки события: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}