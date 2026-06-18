package edu.rutmiit.demo.notificationservice.dto;

public class EventMetadata {
    private String eventId;
    private String eventType;
    private String source;
    private String timestamp;

    public EventMetadata() {}

    public EventMetadata(String eventId, String eventType, String source, String timestamp) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.source = source;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}