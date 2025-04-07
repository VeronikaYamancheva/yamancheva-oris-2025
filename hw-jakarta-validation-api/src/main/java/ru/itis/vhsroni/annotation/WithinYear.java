package ru.itis.vhsroni.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.vhsroni.validators.WithinYearValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = WithinYearValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithinYear {
    String message() default "Дата окончания должна быть менее, чем через год";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
