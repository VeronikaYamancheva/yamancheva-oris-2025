package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.exceptions.*;
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

        String email = request.getEmail();
        String rawPassword = request.getRawPassword();

        emailValidator.checkValid(email);
        passwordValidator.checkValid(rawPassword);

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistServiceException();
        }

        String confirmationCode = generateConfirmationCode();
        boolean emailSent = sendConfirmationCode(email, confirmationCode);

        if (!emailSent) {
            throw new ImpossibleToSendEmailServiceException();
        }

        UserEntity user = userMapper.toEntity(request);
        user.setConfirmationCode(confirmationCode);

        userRepository.save(user);

        return new OperationResponse(0, "Данные приняты, проверочный код отправлен");
    }

    @Override
    public TokenResponse confirmSignUp(SignUpConfirmationRequest request) {

        String email = request.getEmail();
        String confirmationCode = request.getConfirmCode();

        emailValidator.checkValid(email);

        if (confirmationCode == null || confirmationCode.isBlank() || confirmationCode.isEmpty()) {
            throw new EmptyConfirmationCodeValidationServiceException();
        }

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new EmailNotFoundServiceException();
        }

        UserEntity user = userOptional.get();

        if (user.isConfirmed()) {
            throw new EmailIsAlreadyConfirmedServiceException();
        }

        if (!confirmationCode.equals(user.getConfirmationCode())) {
            throw new IncorrectConfirmationCodeValidationServiceException();
        }

        user.setConfirmed(true);
        String token = tokenService.generateToken();
        user.setToken(token);
        userRepository.save(user);

        return new TokenResponse(0, "Пользователь зарегистрирован, предоставлен токен для входа", token);
    }

    public String generateConfirmationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private boolean sendConfirmationCode(String email, String code) {
        //TODO: логика отправки сообщений
        return true;
    }
}
