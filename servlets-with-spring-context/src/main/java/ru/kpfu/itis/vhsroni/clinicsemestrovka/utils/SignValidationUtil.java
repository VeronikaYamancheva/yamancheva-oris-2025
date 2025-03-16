package ru.kpfu.itis.vhsroni.clinicsemestrovka.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SignValidationUtil {

    public boolean checkEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean checkPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$");
    }


    public boolean checkName(String name) {
        return name.matches("^[A-Za-zА-Яа-яЁё]+$");
    }
}
