package edu.rutmiit.demo.booksapicontract.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;
import java.util.regex.Pattern;

/**
 * Проверяет, что год является корректным между 1000 и текущего года включительно.
 */
public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    private static final Integer START = 1000;
    private static final Integer END = Year.now().getValue();

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        // null и пустую строку пропускаем — за них отвечает @NotBlank
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
