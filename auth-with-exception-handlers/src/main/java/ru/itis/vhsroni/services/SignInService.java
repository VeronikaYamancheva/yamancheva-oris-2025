package ru.itis.vhsroni.services;

import ru.itis.vhsroni.api.dto.request.SignInRequest;
import ru.itis.vhsroni.api.dto.response.TokenResponse;

public interface SignInService {

    TokenResponse signInByToken(SignInRequest request);
}
