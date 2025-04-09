package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.vhsroni.api.dto.request.SignUpConfirmationRequest;
import ru.itis.vhsroni.api.dto.request.SignUpRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.exceptions.*;
import ru.itis.vhsroni.mappers.UserMapper;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.TokenService;
import ru.itis.vhsroni.services.impl.BaseSignUpServiceImpl;
import ru.itis.vhsroni.validators.EmailValidator;
import ru.itis.vhsroni.validators.PasswordValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BaseSignUpServiceImplTest {

    private static final String VALID_EMAIL = "test_email@mail.ru";

    private static final String EXISTING_EMAIL = "existingt@email.com";

    private static final String NON_EXISTING_EMAIL = "nonexistingt@email.com";

    private static final String VALID_PASSWORD = "valid-password";

    private static final String VALID_NICKNAME = "nickname";

    private static final String GENERATED_TOKEN = "generated-token";

    private static final String CONFIRMATION_CODE = "123456";

    private static final String WRONG_CODE = "234567";

    private static final SignUpRequest VALID_SIGN_UP_REQUEST = new SignUpRequest(VALID_EMAIL, VALID_PASSWORD, VALID_NICKNAME);

    private static final SignUpRequest SIGN_UP_REQUEST_WITH_EXISTING_EMAIL = new SignUpRequest(EXISTING_EMAIL, VALID_PASSWORD, VALID_NICKNAME);

    private static final SignUpConfirmationRequest VALID_CONFIRMATION_REQUEST = new SignUpConfirmationRequest(VALID_EMAIL, CONFIRMATION_CODE);

    private static final SignUpConfirmationRequest CONFIRMATION_REQUEST_WITH_EMPTY_CODE = new SignUpConfirmationRequest(VALID_EMAIL, "");

    private static final SignUpConfirmationRequest CONFIRMATION_REQUEST_WITH_WRONG_CODE = new SignUpConfirmationRequest(VALID_EMAIL, WRONG_CODE);

    private static final SignUpConfirmationRequest CONFIRMATION_REQUEST_WITH_NON_EXISTING_EMAIL = new SignUpConfirmationRequest(NON_EXISTING_EMAIL, CONFIRMATION_CODE);

    private static final UserEntity UNCONFIRMED_USER = new UserEntity();

    private static final UserEntity CONFIRMED_USER = new UserEntity();

    static {
        UNCONFIRMED_USER.setEmail(VALID_EMAIL);
        UNCONFIRMED_USER.setConfirmed(false);
        UNCONFIRMED_USER.setConfirmationCode(CONFIRMATION_CODE);

        CONFIRMED_USER.setEmail(VALID_EMAIL);
        CONFIRMED_USER.setConfirmed(true);
    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private BaseSignUpServiceImpl signUpService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrepareSignUpSuccess() {
        when(userRepository.existsByEmail(VALID_EMAIL)).thenReturn(false);
        when(userMapper.toEntity(VALID_SIGN_UP_REQUEST)).thenReturn(UNCONFIRMED_USER);

        OperationResponse response = signUpService.prepareSignUp(VALID_SIGN_UP_REQUEST);

        assertEquals(0, response.getOperationStatus());
        assertEquals("Данные приняты, проверочный код отправлен", response.getDescription());
    }

    @Test
    void  testPrepareSignUpWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(EXISTING_EMAIL)).thenReturn(true);

        assertThrows(EmailAlreadyExistServiceException.class, () ->
                signUpService.prepareSignUp(SIGN_UP_REQUEST_WITH_EXISTING_EMAIL));
    }

    @Test
    void testConfirmSignUpSuccess() {
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(UNCONFIRMED_USER));
        when(tokenService.generateToken()).thenReturn(GENERATED_TOKEN);

        TokenResponse response = signUpService.confirmSignUp(VALID_CONFIRMATION_REQUEST);

        assertEquals(0, response.getOperationStatus());
        assertEquals("Пользователь зарегистрирован, предоставлен токен для входа", response.getDescription());
        assertEquals("generated-token", response.getToken());
        assertTrue(UNCONFIRMED_USER.isConfirmed());
    }

    @Test
    void testConfirmSignUpWithEmptyCode() {
        assertThrows(EmptyConfirmationCodeValidationServiceException.class, () ->
                signUpService.confirmSignUp(CONFIRMATION_REQUEST_WITH_EMPTY_CODE));
    }

    @Test
    void testConfirmSignUpEmailNotFound() {
        when(userRepository.findByEmail(NON_EXISTING_EMAIL)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundServiceException.class, () ->
                signUpService.confirmSignUp(CONFIRMATION_REQUEST_WITH_NON_EXISTING_EMAIL));
    }

    @Test
    void testConfirmSignUpWhenAlreadyConfirmed() {
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(CONFIRMED_USER));

        assertThrows(EmailIsAlreadyConfirmedServiceException.class, () ->
                signUpService.confirmSignUp(VALID_CONFIRMATION_REQUEST));
    }

    @Test
    void testConfirmSignUpWithIncorrectCode() {
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(UNCONFIRMED_USER));

        assertThrows(IncorrectConfirmationCodeValidationServiceException.class, () ->
                signUpService.confirmSignUp(CONFIRMATION_REQUEST_WITH_WRONG_CODE));
    }

    @Test
    void testGenerateConfirmationCode() {
        String code = signUpService.generateConfirmationCode();
        assertNotNull(code);
        assertEquals(6, code.length());
        assertDoesNotThrow(() -> Integer.parseInt(code));
    }
}
