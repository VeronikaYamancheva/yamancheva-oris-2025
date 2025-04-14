package ru.itis.vhsroni.api.dto.request;

import lombok.*;

/**
 * Используется завершения регистрации и подтверждения своего email.
 * Пользователь сообщает email и код, отправленный на него
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpConfirmationRequest {

    private String email;

    private String confirmCode;

}
