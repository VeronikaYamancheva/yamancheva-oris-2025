package ru.kpfu.itis.vhsroni.clinicsemestrovka.services;

public interface InputUniqueValidatorService {

    boolean checkUniqueEmail(String email);

    boolean checkUniqueNickname(String nickname);

}
