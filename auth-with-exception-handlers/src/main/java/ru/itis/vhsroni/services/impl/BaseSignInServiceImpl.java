package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.api.dto.request.SignInRequest;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.SignInService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseSignInServiceImpl implements SignInService {

    private final UserRepository userRepository;

    @Override
    public TokenResponse signInByToken(SignInRequest request) {
        try {
            String email = request.getEmail();
            String rawPassword = request.getRawPassword();

            if (email == null || email.isEmpty() || email.isBlank()) {
                return TokenResponse.builder()
                        .operationStatus(3)
                        .description("не сообщён логин")
                        .build();
            }
            if (rawPassword == null || rawPassword.isBlank() || rawPassword.isEmpty()) {
                return TokenResponse.builder()
                        .operationStatus(4)
                        .description("не сообщён пароль")
                        .build();
            }
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                return TokenResponse.builder()
                        .operationStatus(1)
                        .description("сообщён неверный логин")
                        .build();
            }

            UserEntity user = userOptional.get();

            String hashPassword = user.getHashPassword();

            if (!BCrypt.checkpw(rawPassword, hashPassword)) {
                return TokenResponse.builder()
                        .operationStatus(2)
                        .description("сообщён неверный пароль")
                        .build();
            }

            String token = "token-new-" + user.getUuid().toString();
            user.setToken(token);
            userRepository.save(user);

            return TokenResponse.builder()
                    .operationStatus(0)
                    .description("успешный вход")
                    .token(token)
                    .build();

        } catch (Exception E) {
            return TokenResponse.builder()
                    .operationStatus(99)
                    .description("внутренние ошибки сервиса")
                    .build();
        }
    }
}
