package ru.itis.vhsroni.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.vhsroni.annotation.BudgetMultiple;

public class BudgetMultipleValidator implements ConstraintValidator<BudgetMultiple, Double> {

    private int multiple;

    @Override
    public void initialize(BudgetMultiple constraintAnnotation) {
        this.multiple = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value % multiple == 0;
    }
}
