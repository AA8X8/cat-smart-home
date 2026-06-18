package edu.rutmiit.demo.cateventscontract.events;

public final class RoutingKeys {
    private RoutingKeys() {}

    public static final String EXCHANGE = "cat.events";

    // События котов
    public static final String CAT_CREATED = "cat.created";
    public static final String CAT_UPDATED = "cat.updated";
    public static final String CAT_DELETED = "cat.deleted";
    public static final String CAT_ENRICHED = "cat.enriched";  // ← ДОБАВИТЬ ЭТО!

    // События датчиков
    public static final String SENSOR_EVENT = "sensor.event";

    // События действий
    public static final String ACTION_EXECUTED = "action.executed";

    // Паттерны для подписки
    public static final String ALL_CAT_EVENTS = "cat.*";
    public static final String ALL_EVENTS = "#";
}