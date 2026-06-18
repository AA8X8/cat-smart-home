package edu.rutmiit.demo.booksapicontract.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации года.
 * Год публикации должен быть между 1000 и текущим годом
 */
@Documented
@Constraint(validatedBy = YearValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {

    String message() default "Некорректный год. Допустимые форматы: год должен быть между 1000 и текущим годом";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
