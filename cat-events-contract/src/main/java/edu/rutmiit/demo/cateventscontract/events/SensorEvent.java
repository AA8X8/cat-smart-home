package edu.rutmiit.demo.cateventscontract.events;

public sealed interface SensorEvent {
    record Occurred(
            Long eventId,
            String type,
            Long catId,
            String catName,
            Object payload
    ) implements SensorEvent {}
}