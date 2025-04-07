package ru.itis.vhsroni.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Используется для входа в приложение.
 * Пользователь сообщает свои данные для входа
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    private String email;

    private String rawPassword;

}