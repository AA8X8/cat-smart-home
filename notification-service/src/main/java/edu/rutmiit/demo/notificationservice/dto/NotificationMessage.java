package edu.rutmiit.demo.notificationservice.dto;

public class NotificationMessage {
    private String title;
    private String message;
    private String type;
    private long timestamp;

    public NotificationMessage() {}

    public NotificationMessage(String title, String message, String type, long timestamp) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}