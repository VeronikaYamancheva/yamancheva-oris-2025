package ru.itis.vhsroni.validation.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.validation.PasswordValidator;

import java.util.Objects;

@RequiredArgsConstructor
public class PasswordMinimumLengthValidatorImpl implements PasswordValidator {

    private final int minLength;

    @Override
    public boolean validate(String password) {
        if (Objects.isNull(password)) return false;
        return password.length() >= minLength;
    }
}