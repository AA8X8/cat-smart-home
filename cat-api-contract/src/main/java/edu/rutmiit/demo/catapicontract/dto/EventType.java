package edu.rutmiit.demo.catapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип события от датчика или системы")
public enum EventType {
    BOWL_EMPTY,
    LITTER_USED,
    INACTIVITY,
    DOOR_OPENED,
    BOWL_FILLED,
    LITTER_CLEANED,
    TOY_ACTIVATED,
    NOTIFICATION_SENT
}