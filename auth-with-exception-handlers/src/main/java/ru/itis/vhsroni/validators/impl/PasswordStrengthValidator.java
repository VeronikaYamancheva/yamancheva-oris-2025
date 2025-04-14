package ru.itis.vhsroni.validators.impl;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.exceptions.EmptyPasswordValidationServiceException;
import ru.itis.vhsroni.exceptions.IncorrectPasswordValidationServiceException;
import ru.itis.vhsroni.validators.PasswordValidator;

@Component
public class PasswordStrengthValidator implements PasswordValidator {

    @Override
    public void checkValid(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new EmptyPasswordValidationServiceException();
        }
        if (password.length() < 8) {
            throw new IncorrectPasswordValidationServiceException();
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IncorrectPasswordValidationServiceException();
        }
        if (!password.matches(".*[a-z].*")) {
            throw new IncorrectPasswordValidationServiceException();
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IncorrectPasswordValidationServiceException();
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new IncorrectPasswordValidationServiceException();
        }

    }
}
