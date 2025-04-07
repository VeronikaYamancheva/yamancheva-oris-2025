package ru.itis.vhsroni.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.vhsroni.annotation.WithinYear;

import java.time.LocalDate;

public class WithinYearValidator implements ConstraintValidator<WithinYear, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusYears(1);

        return value.isAfter(today) && !value.isAfter(maxDate);
    }
}
