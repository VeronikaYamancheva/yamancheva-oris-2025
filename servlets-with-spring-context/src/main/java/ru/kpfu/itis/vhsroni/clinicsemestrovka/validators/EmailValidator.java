package ru.kpfu.itis.vhsroni.clinicsemestrovka.validators;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailValidator {

    public boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}
