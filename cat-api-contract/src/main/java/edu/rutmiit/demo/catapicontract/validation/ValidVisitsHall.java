package edu.rutmiit.demo.catapicontract.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VisitsHallValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVisitsHall {
    String message() default "Количество посещений должно быть от 1 до 220";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}