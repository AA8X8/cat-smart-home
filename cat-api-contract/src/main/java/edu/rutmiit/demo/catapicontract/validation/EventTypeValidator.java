package edu.rutmiit.demo.catapicontract.validation;

import edu.rutmiit.demo.catapicontract.dto.EventType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventTypeValidator implements ConstraintValidator<ValidEventType, EventType> {
    @Override
    public boolean isValid(EventType value, ConstraintValidatorContext context) {
        return value != null;
    }
}