package ru.itis.vhsroni.api.dto.request;

import lombok.*;

/**
 * Используется для операции выхода из приложения.
 * Сообщается токен пользователя, который хочет выполнить выход
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    private String token;

}
