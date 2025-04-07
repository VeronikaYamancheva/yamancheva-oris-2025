package ru.itis.vhsroni.validators;

public interface EmailValidator {

    boolean checkEmptyValue(String email);

    boolean checkValid(String email);
}
