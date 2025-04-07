package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.api.SignInApi;
import ru.itis.vhsroni.api.dto.request.SignInRequest;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.services.SignInService;

@RestController
@RequiredArgsConstructor
public class SignInController implements SignInApi {

    private final SignInService signInService;

    @Override
    public TokenResponse signInByToken(SignInRequest request) {
        return signInService.signInByToken(request);
    }
}
