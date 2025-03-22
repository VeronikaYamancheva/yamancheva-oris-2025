package ru.kpfu.itis.vhsroni.clinicsemestrovka.validators;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PasswordValidator {

    public boolean checkPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$");
    }
}
