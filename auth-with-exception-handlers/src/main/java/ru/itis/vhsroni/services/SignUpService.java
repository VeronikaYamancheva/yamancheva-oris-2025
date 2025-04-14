package ru.itis.vhsroni.services;

import ru.itis.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.api.dto.response.TokenResponse;

public interface SignUpService {

    OperationResponse prepareSignUp(SignUpRequest request);

    TokenResponse confirmSignUp(SignUpConfirmationRequest request);
}
