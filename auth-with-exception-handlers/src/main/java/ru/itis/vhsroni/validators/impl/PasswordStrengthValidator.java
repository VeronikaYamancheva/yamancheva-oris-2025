package ru.itis.vhsroni.validators.impl;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.validators.PasswordValidator;

@Component
public class PasswordStrengthValidator implements PasswordValidator {

    @Override
    public boolean checkValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            return false;
        }
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}
