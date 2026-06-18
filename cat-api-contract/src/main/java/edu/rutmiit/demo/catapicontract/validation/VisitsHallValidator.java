package edu.rutmiit.demo.catapicontract.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VisitsHallValidator implements ConstraintValidator<ValidVisitsHall, Integer> {

    @Override
    public boolean isValid(Integer visitsHall, ConstraintValidatorContext context) {
        if (visitsHall == null) {
            return true;
        }
        return visitsHall >= 1 && visitsHall <= 220;
    }
}