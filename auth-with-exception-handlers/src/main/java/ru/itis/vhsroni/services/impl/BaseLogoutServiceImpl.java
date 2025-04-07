package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.api.dto.request.LogoutRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.LogoutService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseLogoutServiceImpl implements LogoutService {

    private final UserRepository userRepository;

    @Override
    public OperationResponse logout(LogoutRequest request) {
        try {
            String token = request.getToken();

            if (token == null || token.isEmpty() || token.isBlank()) {
                return new OperationResponse(2, "не предоставлен токен");
            }
            Optional<UserEntity> userOptional = userRepository.findByToken(token);

            if (userOptional.isEmpty()) {
                return new OperationResponse(1, "предоставлен недействительный токен");
            }

            UserEntity user = userOptional.get();

            user.setToken(null);
            userRepository.save(user);

            return new OperationResponse(0, "успешный выход");
        } catch (Exception e) {
            return new OperationResponse(99, "внутренние ошибки сервиса");
        }
    }
}
