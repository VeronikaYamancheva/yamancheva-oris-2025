package ru.itis.vhsroni.validation.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.validation.EmailValidator;

import java.util.Objects;

@RequiredArgsConstructor
public class EmailMinimumLengthValidatorImpl implements EmailValidator {

    private final int minLength;

    @Override
    public void validate(String email) {
        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email is null");
        }
        if (email.isBlank() || email.isEmpty()) {
            throw new IllegalArgumentException("Email is empty");
        }
        if (email.length() < minLength) {
            throw new IllegalArgumentException("Email too short");
        }
    }
}
