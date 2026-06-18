package edu.rutmiit.demo.catgrpcanalyticsserver.service;

import edu.rutmiit.demo.grpc.CatAnalyticsGrpc;  // ← ВАЖНО: правильный импорт
import edu.rutmiit.demo.grpc.AnalyzeCatRequest;
import edu.rutmiit.demo.grpc.CatAnalysisResponse;
import edu.rutmiit.demo.grpc.PredictActivityRequest;
import edu.rutmiit.demo.grpc.PredictActivityResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatAnalyticsServiceImpl extends CatAnalyticsGrpc.CatAnalyticsImplBase {
    private static final Logger log = LoggerFactory.getLogger(CatAnalyticsServiceImpl.class);

    public void analyzeCat(AnalyzeCatRequest request, StreamObserver<CatAnalysisResponse> responseObserver) {
        log.info("Анализ кота: id={}, name={}, breed={}, visitsHall={}, eventsCount={}",
                request.getCatId(), request.getName(), request.getBreed(),
                request.getVisitsHall(), request.getEventsCount());

        int visitsHall = request.getVisitsHall();
        double activityHours = request.getActivityHours();
        int eventsCount = request.getEventsCount();

        String activityLevel;
        String healthStatus;
        double score;
        String suggestion;
        String toys;

        if (visitsHall > 30 || activityHours > 5 || eventsCount > 20) {
            activityLevel = "HIGH";
            healthStatus = "GOOD";
            score = 9.0;
            suggestion = "Кот очень активен! Продолжайте в том же духе.";
            toys = "Лазерная указка, интерактивный мяч";
        } else if (visitsHall > 10 || activityHours > 2 || eventsCount > 8) {
            activityLevel = "MEDIUM";
            healthStatus = "GOOD";
            score = 7.0;
            suggestion = "Хороший уровень активности. Поиграйте с котом побольше.";
            toys = "Мышь на верёвочке, игрушка-дразнилка";
        } else {
            activityLevel = "LOW";
            healthStatus = "NEEDS_ATTENTION";
            score = 3.0;
            suggestion = "Кот малоактивен. Обратите внимание на здоровье и окружение.";
            toys = "Новые игрушки для стимуляции активности";
        }

        CatAnalysisResponse response = CatAnalysisResponse.newBuilder()
                .setCatId(request.getCatId())
                .setActivityLevel(activityLevel)
                .setHealthStatus(healthStatus)
                .setRecommendationScore(score)
                .setEnrichmentSuggestion(suggestion)
                .setRecommendedToys(toys)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    public void predictActivity(PredictActivityRequest request, StreamObserver<PredictActivityResponse> responseObserver) {
        String timeOfDay = request.getTimeOfDay();
        double predictedHours;

        switch (timeOfDay) {
            case "MORNING" -> predictedHours = 1.5;
            case "AFTERNOON" -> predictedHours = 0.8;
            case "EVENING" -> predictedHours = 2.5;
            case "NIGHT" -> predictedHours = 0.2;
            default -> predictedHours = 1.0;
        }

        PredictActivityResponse response = PredictActivityResponse.newBuilder()
                .setCatId(request.getCatId())
                .setPredictedActivityHours(predictedHours)
                .setMostActivePeriod("EVENING")
                .setRecommendation("Лучшее время для игр - вечером с 18 до 21 часа")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}