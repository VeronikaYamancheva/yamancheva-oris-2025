package ru.itis.vhsroni.api.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Ответ для сценариев завершения регистрации или входа.
 * Может быть ненулевой статус операции и описание, либо нулевой код и токен для входа в приложение
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenResponse extends OperationResponse {

    private String token;

    public TokenResponse(int operationStatus, String description, String token) {
        super(operationStatus, description);
        this.token = token;
    }
}
