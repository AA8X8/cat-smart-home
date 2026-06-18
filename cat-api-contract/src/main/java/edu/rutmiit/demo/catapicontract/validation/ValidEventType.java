package edu.rutmiit.demo.catapicontract.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EventTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventType {
    String message() default "Недопустимый тип события";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}