package ru.itis.vhsroni.validators.impl;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.validators.EmailValidator;

@Component
public class EmailFormatValidator implements EmailValidator {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public boolean checkValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(EMAIL_PATTERN);
    }

    @Override
    public boolean checkEmptyValue(String email) {
        return email == null || email.isEmpty() || email.isBlank();
    }
}
