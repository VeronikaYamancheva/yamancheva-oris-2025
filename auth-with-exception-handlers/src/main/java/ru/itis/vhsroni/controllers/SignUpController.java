package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.api.SignUpApi;
import ru.itis.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.services.SignUpService;

@RestController
@RequiredArgsConstructor
public class SignUpController implements SignUpApi {

    private final SignUpService signUpService;

    @Override
    public OperationResponse prepareSignUp(SignUpRequest request) {
        return signUpService.prepareSignUp(request);
    }

    @Override
    public TokenResponse confirmSignUp(SignUpConfirmationRequest request) {
        return signUpService.confirmSignUp(request);
    }
}
