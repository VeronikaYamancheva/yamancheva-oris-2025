package ru.itis.vhsroni.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.vhsroni.validators.BudgetMultipleValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = BudgetMultipleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BudgetMultiple {
    int value() default 1000;

    String message() default "Бюджет должен быть кратен {}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
