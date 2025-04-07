package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.api.dto.request.SignInRequest;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.SignInService;
import ru.itis.vhsroni.services.TokenService;
import ru.itis.vhsroni.validators.EmailValidator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseSignInServiceImpl implements SignInService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final EmailValidator emailValidator;

    @Override
    public TokenResponse signInByToken(SignInRequest request) {
        try {

            String email = request.getEmail();
            String rawPassword = request.getRawPassword();

            if (emailValidator.checkEmptyValue(email)) {
                return new TokenResponse(3, "не сообщён логин", null);
            }
            if (rawPassword == null || rawPassword.isBlank() || rawPassword.isEmpty()) {
                return new TokenResponse(4, "не сообщён пароль", null);
            }

            Optional<UserEntity> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                return new TokenResponse(1, "сообщён неверный логин", null);
            }

            UserEntity user = userOptional.get();

            String hashPassword = user.getHashPassword();

            if (!BCrypt.checkpw(rawPassword, hashPassword)) {
                return new TokenResponse(2, "сообщён неверный пароль", null);
            }

            String token = tokenService.generateToken();
            user.setToken(token);
            userRepository.save(user);

            return new TokenResponse(0, "успешный вход", token);

        } catch (Exception E) {
            return new TokenResponse(99, "внутренние ошибки сервиса", null);
        }
    }
}
