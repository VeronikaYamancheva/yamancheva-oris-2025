package ru.vhsroni.classwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.vhsroni.api.SignUpApi;
import ru.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.vhsroni.api.dto.request.SignUpRequest;
import ru.vhsroni.api.dto.response.OperationResponse;
import ru.vhsroni.api.dto.response.TokenResponse;
import ru.vhsroni.classwork.services.AuthService;

@RestController
@RequiredArgsConstructor
public class SignUpController implements SignUpApi {

    private final AuthService authService;

    @Override
    public OperationResponse prepareSignUp(SignUpRequest request) {
        return authService.register(request);
    }

    @Override
    public TokenResponse confirmSignUp(SignUpConfirmationRequest request) {
        return authService.confirmRegistration(request);
    }
}
