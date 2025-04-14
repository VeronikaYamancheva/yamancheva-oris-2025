package ru.itis.vhsroni.services;

import ru.itis.vhsroni.api.dto.request.LogoutRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;

public interface LogoutService {

    OperationResponse logout(LogoutRequest request);
}
