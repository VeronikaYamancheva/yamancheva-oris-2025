package ru.itis.vhsroni.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.api.dto.response.TokenResponse;

/**
 * Апи предназначено для регистрации пользователя в приложении.
 * Регистрация происходит в два этапа - сначала пользователь задаёт свои данные,
 * и отправляется проверочный код на его email.
 * Вторым шагом пользователь подтверждает свой email этим кодом.
 */

@RequestMapping(
        value = "/sign-up",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@ResponseBody
public interface SignUpApi {

    /**
     * Запуск процесса регистрации пользователя.
     *
     * Статус 0 - данные приняты, проверочный код отправлен
     * Статус 1 - некорректный email;
     * Статус 2 - занятый email;
     * Статус 3 - некорректный пароль;
     * Статус 4 - не предоставлен email;
     * Статус 5 - не предоставлен пароль;
     * Статус 99 - прочие ошибки. Например, внутренние ошибки сервиса или невозможность отправить email
     */
    @PostMapping("/start")
    OperationResponse prepareSignUp(@RequestBody SignUpRequest request);

    /**
     * Завершение процесса регистрации пользователя.
     *
     * Статус 0 - пользователь зарегистрирован, предоставлен токен для входа;
     * Статус 1 - некорректный email;
     * Статус 3 - на предоставленный email код не запрашивался (email уже подтверждён);
     * Статус 4 - не предоставлен email;
     * Статус 5 - некорректный проверочный код;
     * Статус 6 - не предоставлен проверочный код;
     * Статус 99 - прочие ошибки. Например, внутренние ошибки сервиса
     */
    @PostMapping("/confirm")
    TokenResponse confirmSignUp(@RequestBody SignUpConfirmationRequest request);

}
