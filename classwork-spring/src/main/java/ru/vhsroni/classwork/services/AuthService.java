package ru.vhsroni.classwork.services;

import ru.vhsroni.api.dto.request.LogoutRequest;
import ru.vhsroni.api.dto.request.SignInRequest;
import ru.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.vhsroni.api.dto.request.SignUpRequest;
import ru.vhsroni.api.dto.response.OperationResponse;
import ru.vhsroni.api.dto.response.TokenResponse;
import ru.vhsroni.classwork.dto.UserCredentials;
import ru.vhsroni.classwork.dto.VerificationData;
import ru.vhsroni.classwork.entity.TokenEntity;
import ru.vhsroni.classwork.entity.UserEntity;
import ru.vhsroni.classwork.exceptions.AuthException;
import ru.vhsroni.classwork.repositories.TokenRepository;
import ru.vhsroni.classwork.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    public OperationResponse register(SignUpRequest request);

    public TokenResponse confirmRegistration(SignUpConfirmationRequest request);
    TokenResponse login(SignInRequest request);

    public OperationResponse logout(LogoutRequest request);
}
