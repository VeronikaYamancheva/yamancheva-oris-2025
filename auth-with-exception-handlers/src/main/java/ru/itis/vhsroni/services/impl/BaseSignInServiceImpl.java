package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.api.dto.request.SignInRequest;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.exceptions.EmailNotFoundServiceException;
import ru.itis.vhsroni.exceptions.IncorrectPasswordValidationServiceException;
import ru.itis.vhsroni.exceptions.InternalServiceException;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.SignInService;
import ru.itis.vhsroni.services.TokenService;
import ru.itis.vhsroni.validators.EmailValidator;
import ru.itis.vhsroni.validators.PasswordValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseSignInServiceImpl implements SignInService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    @Override
    public TokenResponse signInByToken(SignInRequest request) {
        String email = request.getEmail();
        String rawPassword = request.getRawPassword();

        emailValidator.checkValid(email);
        passwordValidator.checkValid(rawPassword);


        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new EmailNotFoundServiceException();
        }

        UserEntity user = userOptional.get();
        String hashPassword = user.getHashPassword();

        if (!BCrypt.checkpw(rawPassword, hashPassword)) {
            throw new IncorrectPasswordValidationServiceException();
        }

        String token = tokenService.generateToken();
        user.setToken(token);
        userRepository.save(user);

        return new TokenResponse(0, "успешный вход", token);
    }
}
