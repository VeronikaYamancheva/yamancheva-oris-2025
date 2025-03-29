package ru.vhsroni.classwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.vhsroni.api.LogoutApi;
import ru.vhsroni.api.dto.request.LogoutRequest;
import ru.vhsroni.api.dto.response.OperationResponse;
import ru.vhsroni.classwork.services.AuthService;

@RestController
@RequiredArgsConstructor
public class LogoutController implements LogoutApi {

    private final AuthService authService;

    @Override
    public OperationResponse logout(LogoutRequest request) {
        return authService.logout(request);
    }
}
