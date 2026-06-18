package edu.rutmiit.demo.catapicontract.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус выполнения действия")
public enum ActionStatus {
    SUCCESS, ERROR
}