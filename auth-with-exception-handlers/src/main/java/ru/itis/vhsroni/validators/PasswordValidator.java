package ru.itis.vhsroni.validators;

public interface PasswordValidator {

    boolean checkEmptyValue(String password);
    boolean checkValid(String password);
}
