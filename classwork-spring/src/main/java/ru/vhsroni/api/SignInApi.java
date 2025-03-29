package ru.vhsroni.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vhsroni.api.dto.request.SignInRequest;
import ru.vhsroni.api.dto.response.TokenResponse;

/**
 * Апи предназначено для входа пользователя в приложение.
 */

@RequestMapping(
        value = "/sign-in",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@ResponseBody
public interface SignInApi {

    /**
     * При операции входа проверяются данные пользователя,
     * и при успехе выдаётся новый действительный токен.
     * Старый токен при этом (если он был) должен стать недействительным
     * и\или быть удалён из системы.
     *
     * Статус 0 - успешный вход;
     * Статус 1 - сообщён неверный логин;
     * Статус 2 - сообщён неверный пароль;
     * Статус 3 - не сообщён логин;
     * Статус 4 - не сообщён пароль;
     * Статус 99 - прочие ошибки. Например, внутренние ошибки сервиса
     */
    @PostMapping
    TokenResponse signInByToken(@RequestBody SignInRequest request);

}