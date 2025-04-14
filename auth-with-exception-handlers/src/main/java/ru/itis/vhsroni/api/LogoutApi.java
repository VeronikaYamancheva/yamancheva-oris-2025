package ru.itis.vhsroni.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.vhsroni.api.dto.request.LogoutRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;

/**
 * Апи предназначено для выхода пользователя из приложения.
 */

@RequestMapping(
        value = "/logout",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@ResponseBody
public interface LogoutApi {

    /**
     * При запросе выхода токен пользователя должен стать недействительным
     * и\или быть удалён из системы.
     *
     * Статус 0 - успешный выход;
     * Статус 1 - предоставлен недействительный токен;
     * Статус 2 - не предоставлен токен;
     * Статус 99 - прочие ошибки. Например, внутренние ошибки сервиса
     */
    @PostMapping
    OperationResponse logout(@RequestBody LogoutRequest request);

}
