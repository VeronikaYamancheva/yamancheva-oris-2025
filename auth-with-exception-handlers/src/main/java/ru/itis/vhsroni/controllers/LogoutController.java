package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.api.LogoutApi;
import ru.itis.vhsroni.api.dto.request.LogoutRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.services.LogoutService;

@RestController
@RequiredArgsConstructor
public class LogoutController implements LogoutApi {

    private final LogoutService logoutService;

    @Override
    public OperationResponse logout(LogoutRequest request) {
        return logoutService.logout(request);
    }
}
