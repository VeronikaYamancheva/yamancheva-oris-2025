package ru.itis.vhsroni.validation.impl;

import ru.itis.vhsroni.validation.EmailValidator;

import java.util.Objects;

public class EmailPatternValidatorImpl implements EmailValidator {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Override
    public void validate(String email) {
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email is null");
        }
        if (email.isBlank() || email.isEmpty()) {
            throw new IllegalArgumentException("Email is empty");
        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Email doesn't match pattern");
        }
    }
}
