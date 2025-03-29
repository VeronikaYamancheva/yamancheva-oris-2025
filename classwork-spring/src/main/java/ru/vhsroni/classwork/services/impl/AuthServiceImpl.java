package ru.vhsroni.classwork.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vhsroni.api.dto.request.LogoutRequest;
import ru.vhsroni.api.dto.request.SignInRequest;
import ru.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.vhsroni.api.dto.request.SignUpRequest;
import ru.vhsroni.api.dto.response.OperationResponse;
import ru.vhsroni.api.dto.response.TokenResponse;
import ru.vhsroni.classwork.entity.TokenEntity;
import ru.vhsroni.classwork.entity.UserEntity;
import ru.vhsroni.classwork.repositories.TokenRepository;
import ru.vhsroni.classwork.repositories.UserRepository;
import ru.vhsroni.classwork.services.AuthService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public OperationResponse register(SignUpRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return createErrorResponse(4, "Email not provided");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return createErrorResponse(2, "Email already taken");
        }

        if (request.getRawPassword() == null || request.getRawPassword().isEmpty()) {
            return createErrorResponse(5, "Password not provided");
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(request.getRawPassword());
        user.setNickName(request.getNickName());
        user.setEmailVerified(false);
        userRepository.save(user);

        return createSuccessResponse(0, "Verification code sent");
    }

    public TokenResponse confirmRegistration(SignUpConfirmationRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return createTokenErrorResponse(4, "Email not provided");
        }

        Optional<UserEntity> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return createTokenErrorResponse(1, "User not found");
        }

        UserEntity user = userOpt.get();
        user.setEmailVerified(true);
        userRepository.save(user);

        String token = generateToken(user.getId());
        return createTokenSuccessResponse(token);
    }

    public TokenResponse login(SignInRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return createTokenErrorResponse(3, "Email not provided");
        }

        Optional<UserEntity> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return createTokenErrorResponse(1, "User not found");
        }

        UserEntity user = userOpt.get();
        if (!user.getPassword().equals(request.getRawPassword())) {
            return createTokenErrorResponse(2, "Invalid password");
        }

        tokenRepository.deleteByUserId(user.getId());
        String token = generateToken(user.getId());
        return createTokenSuccessResponse(token);
    }

    public OperationResponse logout(LogoutRequest request) {
        if (request.getToken() == null || request.getToken().isEmpty()) {
            return createErrorResponse(2, "Token not provided");
        }

        Optional<TokenEntity> tokenOpt = tokenRepository.findByTokenValue(request.getToken());
        if (tokenOpt.isEmpty()) {
            return createErrorResponse(1, "Invalid token");
        }

        tokenRepository.delete(tokenOpt.get());
        return createSuccessResponse(0, "Successfully logged out");
    }

    private String generateToken(Long userId) {
        String tokenValue = UUID.randomUUID().toString();
        TokenEntity token = new TokenEntity();
        token.setTokenValue(tokenValue);
        token.setUserId(userId);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusDays(30));
        tokenRepository.save(token);
        return tokenValue;
    }

    private OperationResponse createErrorResponse(int status, String description) {
        return OperationResponse.builder()
                .operationStatus(status)
                .description(description)
                .build();
    }

    private OperationResponse createSuccessResponse(int status, String description) {
        return OperationResponse.builder()
                .operationStatus(status)
                .description(description)
                .build();
    }

    private TokenResponse createTokenErrorResponse(int status, String description) {
        TokenResponse response = new TokenResponse();
        response.setOperationStatus(status);
        response.setDescription(description);
        return response;
    }

    private TokenResponse createTokenSuccessResponse(String token) {
        TokenResponse response = new TokenResponse();
        response.setOperationStatus(0);
        response.setToken(token);
        return response;
    }
}

