package ru.itis.vhsroni.service.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.vhsroni.blacklist.PasswordBlacklistRepository;
import ru.itis.vhsroni.service.SignUpService;
import ru.itis.vhsroni.validation.EmailValidator;
import ru.itis.vhsroni.validation.PasswordValidator;

@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    private final PasswordBlacklistRepository passwordBlacklistRepository;

    @Override
    public void signUp(String email, String password) {
        emailValidator.validate(email);

        if (passwordBlacklistRepository.contains(password)) {
            throw new IllegalArgumentException("Password is known");
        }

        if (!passwordValidator.validate(password)) {
            throw new IllegalArgumentException("Password incorrect");
        }
    }
}
