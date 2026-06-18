package edu.rutmiit.demo.cateventscontract.events;

public sealed interface CatEvent {

    record Created(
            Long catId,
            String name,
            String breed,
            String description,
            String activityStatus,
            Integer visitsHall   // ← новое поле
    ) implements CatEvent {}

    record Updated(
            Long catId,
            String name,
            String breed,
            String description,
            String activityStatus
    ) implements CatEvent {}

    record Deleted(
            Long catId,
            String name
    ) implements CatEvent {}

    record Enriched(
           Long catId,
           String name,
           String activityLevel,
           String healthStatus,
           double recommendationScore,
           String enrichmentSuggestion,
           String recommendedToys
    ) implements CatEvent {}
}