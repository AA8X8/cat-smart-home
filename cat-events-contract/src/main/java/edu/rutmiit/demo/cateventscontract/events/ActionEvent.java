package edu.rutmiit.demo.cateventscontract.events;

public sealed interface ActionEvent {
    record Executed(
            Long actionId,
            String type,
            Long catId,
            String catName,
            String status,
            String message
    ) implements ActionEvent {}
}