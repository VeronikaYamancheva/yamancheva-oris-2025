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
                return OperationResponse.builder()
                        .operationStatus(2)
                        .description("не предоставлен токен")
                        .build();
            }
            Optional<UserEntity> userOptional = userRepository.findByToken(token);

            if (userOptional.isEmpty()) {
                return OperationResponse.builder()
                        .operationStatus(1)
                        .description("предоставлен недействительный токен")
                        .build();
            }

            UserEntity user = userOptional.get();

            user.setToken(null);
            userRepository.save(user);

            return OperationResponse.builder()
                    .operationStatus(0)
                    .description("успешный выход")
                    .build();
        } catch (Exception e) {
            return OperationResponse.builder()
                    .operationStatus(99)
                    .description("внутренние ошибки сервиса")
                    .build();
        }
    }
}
