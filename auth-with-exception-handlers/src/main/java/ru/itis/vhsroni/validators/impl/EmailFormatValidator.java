package ru.itis.vhsroni.validators.impl;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.exceptions.EmptyEmailValidationServiceException;
import ru.itis.vhsroni.exceptions.IncorrectEmailValidationServiceException;
import ru.itis.vhsroni.validators.EmailValidator;

@Component
public class EmailFormatValidator implements EmailValidator {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public void checkValid(String email) {
        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new EmptyEmailValidationServiceException();
        }
        if (!email.matches(EMAIL_PATTERN)) {
            throw new IncorrectEmailValidationServiceException();
        }
    }
}
