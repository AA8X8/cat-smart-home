package edu.rutmiit.demo.booksapicontract.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

/**
 * Проверяет, что год является корректным между 1000 и 2026.
 * <p>
 * Алгоритмическая проверка контрольной цифры (алгоритм Луна) намеренно не выполняется:
 * валидатор должен быть быстрым, а точность важна на уровне интеграционных тестов.
 */
public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    private static final Integer START = 1000;
    private static final Integer END = Year.now().getValue();

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) {
            return true;
        }
        if (year < 0) {
            return false;
        }

        if (year >= START && year <= END) return true;
        else return false;

    }
}
