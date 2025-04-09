package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.vhsroni.api.dto.request.SignInRequest;
import ru.itis.vhsroni.api.dto.response.TokenResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.exceptions.EmailNotFoundServiceException;
import ru.itis.vhsroni.exceptions.IncorrectEmailValidationServiceException;
import ru.itis.vhsroni.exceptions.IncorrectPasswordValidationServiceException;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.TokenService;
import ru.itis.vhsroni.services.impl.BaseSignInServiceImpl;
import ru.itis.vhsroni.validators.EmailValidator;
import ru.itis.vhsroni.validators.PasswordValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BaseSignInServiceImplTest {

    private static final String VALID_EMAIL = "test_email@mail.ru";

    private static final String NON_EXISTING_EMAIL = "nonexistent@email.com";

    private static final String INVALID_EMAIL = "email";

    private static final String VALID_PASSWORD = "valid-password";

    private static final String WRONG_PASSWORD = "wrong-password";

    private static final String INVALID_PASSWORD = "password";

    private static final String GENERATED_TOKEN = "generated-token";

    private static final SignInRequest VALID_SIGN_IN_REQUEST = new SignInRequest(VALID_EMAIL, VALID_PASSWORD);

    private static final SignInRequest SIGN_IN_REQUEST_WITH_WRONG_EMAIL = new SignInRequest(NON_EXISTING_EMAIL, VALID_PASSWORD);

    private static final SignInRequest SIGN_IN_REQUEST_WITH_INVALID_EMAIL = new SignInRequest(INVALID_EMAIL, VALID_PASSWORD);

    private static final SignInRequest SIGN_IN_REQUEST_WITH_WRONG_PASSWORD = new SignInRequest(VALID_EMAIL, WRONG_PASSWORD);

    private static final SignInRequest SIGN_IN_REQUEST_WITH_INVALID_PASSWORD = new SignInRequest(VALID_EMAIL, INVALID_PASSWORD);

    private static final UserEntity EXISTING_USER = new UserEntity();

    static {
        EXISTING_USER.setEmail(VALID_EMAIL);
        EXISTING_USER.setHashPassword(BCrypt.hashpw(VALID_PASSWORD, BCrypt.gensalt()));
    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PasswordValidator passwordValidator;

    @InjectMocks
    private BaseSignInServiceImpl signInService;


    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignInByTokenSuccess() {
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(EXISTING_USER));
        when(tokenService.generateToken()).thenReturn(GENERATED_TOKEN);

        TokenResponse response = signInService.signInByToken(VALID_SIGN_IN_REQUEST);

        assertEquals(0, response.getOperationStatus());
        assertEquals("успешный вход", response.getDescription());
        assertEquals(GENERATED_TOKEN, response.getToken());
    }

    @Test
    void testSignInByTokenEmailNotExist() {
        when(userRepository.findByEmail(NON_EXISTING_EMAIL)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundServiceException.class, () ->
            signInService.signInByToken(SIGN_IN_REQUEST_WITH_WRONG_EMAIL)
        );

    }

    @Test
    void testSignInByTokenWithIncorrectPassword() {
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(EXISTING_USER));

        assertThrows(IncorrectPasswordValidationServiceException.class, () ->
            signInService.signInByToken(SIGN_IN_REQUEST_WITH_WRONG_PASSWORD)
        );
    }

    @Test
    void testSignInByTokenWithInvalidEmail() {
        doThrow(new IncorrectEmailValidationServiceException()).when(emailValidator).checkValid(INVALID_EMAIL);

        assertThrows(IncorrectEmailValidationServiceException.class, () ->
            signInService.signInByToken(SIGN_IN_REQUEST_WITH_INVALID_EMAIL)
        );
    }

    @Test
    void testSignInByTokenWithInvalidPassword() {
        doThrow(new IncorrectPasswordValidationServiceException()).when(passwordValidator).checkValid(INVALID_PASSWORD);

        assertThrows(IncorrectPasswordValidationServiceException.class, () ->
            signInService.signInByToken(SIGN_IN_REQUEST_WITH_INVALID_PASSWORD)
        );
    }
}
