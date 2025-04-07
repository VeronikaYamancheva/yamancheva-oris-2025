package ru.itis.vhsroni.api.dto.request;

import lombok.*;

/**
 * Используется для запуска процесса регистрации.
 * Пользователь сообщает свои данные
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String email;

    private String rawPassword;

    private String nickName;

}
