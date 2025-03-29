package ru.vhsroni.classwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.vhsroni.api.SignInApi;
import ru.vhsroni.api.dto.request.SignInRequest;
import ru.vhsroni.api.dto.response.TokenResponse;
import ru.vhsroni.classwork.services.AuthService;

@RestController
@RequiredArgsConstructor
public class SignInController implements SignInApi {
    private final AuthService authService;

    @Override
    public TokenResponse signInByToken(SignInRequest request) {
        return authService.login(request);
    }
}

