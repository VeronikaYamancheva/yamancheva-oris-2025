package ru.kpfu.itis.vhsroni.clinicsemestrovka.validators;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UsernameValidator {

    public boolean checkName(String name) {
        return name.matches("^[A-Za-zА-Яа-яЁё]+$");
    }
}
