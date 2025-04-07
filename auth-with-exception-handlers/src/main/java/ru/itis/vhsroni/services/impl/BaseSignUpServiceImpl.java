package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.mappers.UserMapper;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.SignUpService;
import ru.itis.vhsroni.validators.EmailValidator;
import ru.itis.vhsroni.validators.PasswordValidator;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BaseSignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final EmailValidator emailValidator;

    private final PasswordValidator passwordValidator;

    @Override
    public OperationResponse prepareSignUp(SignUpRequest request) {
        try {
            String email = request.getEmail();
            String rawPassword = request.getRawPassword();

            if (email == null || email.isEmpty() || email.isBlank()) {
                return OperationResponse.builder()
                        .operationStatus(4)
                        .description("не предоставлен email")
                        .build();
            } else if (rawPassword == null || rawPassword.isBlank() || rawPassword.isEmpty()) {
                return OperationResponse.builder()
                        .operationStatus(5)
                        .description("не предоставлен пароль")
                        .build();
            } else if (!emailValidator.checkValid(email)) {
                return OperationResponse.builder()
                        .operationStatus(1)
                        .description("некорректный email")
                        .build();
            } else if (!passwordValidator.checkValid(rawPassword)) {
                return OperationResponse.builder()
                        .operationStatus(3)
                        .description("некорректный пароль")
                        .build();
            } else if (userRepository.existsByEmail(email)) {
                return OperationResponse.builder()
                        .operationStatus(2)
                        .description("занятый email")
                        .build();
            }

            String confirmationCode = generateConfirmationCode();
            boolean emailSent = sendConfirmationCode(email, confirmationCode);

            if (!emailSent) {
                return OperationResponse.builder()
                        .operationStatus(99)
                        .description("невозможность отправить email")
                        .build();
            }

            UserEntity user = userMapper.toEntity(request);
            user.setConfirmationCode(confirmationCode);

            userRepository.save(user);

            return OperationResponse.builder()
                    .operationStatus(0)
                    .description("данные приняты, проверочный код отправлен")
                    .build();

        } catch (Exception e) {
            return OperationResponse.builder()
                    .operationStatus(99)
                    .description("внутренние ошибки сервиса")
                    .build();
        }
    }

    @Override
    public TokenResponse confirmSignUp(SignUpConfirmationRequest request) {
        try {
            String email = request.getEmail();
            String confirmationCode = request.getConfirmCode();

            if (email == null || email.isEmpty() || email.isBlank()) {
                return TokenResponse.builder()
                        .operationStatus(4)
                        .description("не предоставлен email")
                        .build();
            } else if (confirmationCode == null || confirmationCode.isBlank() || confirmationCode.isEmpty()) {
                return TokenResponse.builder()
                        .operationStatus(6)
                        .description("не предоставлен проверочный код")
                        .build();
            } else if (!emailValidator.checkValid(email)) {
                return TokenResponse.builder()
                        .operationStatus(1)
                        .description("некорректный email")
                        .build();
            }

            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return TokenResponse.builder()
                        .operationStatus(3)
                        .description("на предоставленный email код не запрашивался")
                        .build();
            }

            UserEntity user = userOptional.get();
            if (user.isConfirmed()) {
                return TokenResponse.builder()
                        .operationStatus(3)
                        .description("email уже подтверждён")
                        .build();
            }

            if (!confirmationCode.equals(user.getConfirmationCode())) {
                return TokenResponse.builder()
                        .operationStatus(5)
                        .description("некорректный проверочный код")
                        .build();
            }
            user.setConfirmed(true);
            String token = "token-first-" + user.getUuid().toString();
            user.setToken(token);
            userRepository.save(user);

            return TokenResponse.builder()
                    .operationStatus(0)
                    .description("пользователь зарегистрирован, предоставлен токен для входа")
                    .token(token)
                    .build();

        } catch (Exception e) {
            return TokenResponse.builder()
                    .operationStatus(99)
                    .description("внутренние ошибки сервиса")
                    .build();
        }
    }

    private String generateConfirmationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private boolean sendConfirmationCode(String email, String code) {
        return true;
    }
}
