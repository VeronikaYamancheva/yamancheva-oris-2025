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
import ru.itis.vhsroni.services.TokenService;
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

    private final TokenService tokenService;

    @Override
    public OperationResponse prepareSignUp(SignUpRequest request) {
        try {
            String email = request.getEmail();
            String rawPassword = request.getRawPassword();

            if (emailValidator.checkEmptyValue(email)) {
                return new OperationResponse(4, "не предоставлен email");
            }
            if (passwordValidator.checkEmptyValue(rawPassword)) {
                return new OperationResponse(5, "не предоставлен пароль");
            }
            if (!emailValidator.checkValid(email)) {
                return new OperationResponse(1, "некорректный email");
            }
            if (!passwordValidator.checkValid(rawPassword)) {
                return new OperationResponse(3, "некорректный пароль");
            }
            if (userRepository.existsByEmail(email)) {
                return new OperationResponse(2, "занятый email");
            }

            String confirmationCode = generateConfirmationCode();
            boolean emailSent = sendConfirmationCode(email, confirmationCode);

            if (!emailSent) {
                return new OperationResponse(99, "невозможность отправить email");
            }

            UserEntity user = userMapper.toEntity(request);
            user.setConfirmationCode(confirmationCode);

            userRepository.save(user);

            return new OperationResponse(0, "данные приняты, проверочный код отправлен");
        } catch (Exception e) {
            return new OperationResponse(99, "внутренние ошибки сервиса");
        }
    }

    @Override
    public TokenResponse confirmSignUp(SignUpConfirmationRequest request) {
        try {

            String email = request.getEmail();
            String confirmationCode = request.getConfirmCode();

            if (emailValidator.checkEmptyValue(email)) {
                return new TokenResponse(4, "не предоставлен email", null);
            }
            if (confirmationCode == null || confirmationCode.isBlank() || confirmationCode.isEmpty()) {
                return new TokenResponse(6, "не предоставлен проверочный код", null);
            }
            if (!emailValidator.checkValid(email)) {
                return new TokenResponse(1, "некорректный email", null);
            }

            Optional<UserEntity> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                return new TokenResponse(3, "на предоставленный email код не запрашивался",
                        null);
            }

            UserEntity user = userOptional.get();

            if (user.isConfirmed()) {
                return new TokenResponse(3, "email уже подтверждён", null);
            }

            if (!confirmationCode.equals(user.getConfirmationCode())) {
                return new TokenResponse(5, "некорректный проверочный код", null);
            }

            user.setConfirmed(true);
            String token = tokenService.generateToken();
            user.setToken(token);
            userRepository.save(user);

            return new TokenResponse(0, "пользователь зарегистрирован, предоставлен токен для входа", token);

        } catch (Exception e) {
            return new TokenResponse(99, "внутренние ошибки сервиса", null);
        }
    }

    private String generateConfirmationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private boolean sendConfirmationCode(String email, String code) {
        //TODO: логика отправки сообщений
        return true;
    }
}
