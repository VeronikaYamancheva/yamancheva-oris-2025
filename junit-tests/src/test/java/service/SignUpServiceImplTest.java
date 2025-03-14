package service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.blacklist.PasswordBlacklistRepository;
import ru.itis.vhsroni.service.impl.SignUpServiceImpl;
import ru.itis.vhsroni.validation.EmailValidator;
import ru.itis.vhsroni.validation.PasswordValidator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignUpServiceImplTest {

    private static final String GOOD_EMAIL = "goodEmail";

    private static final String BAD_EMAIL = "badEmail";

    private static final String GOOD_PASSWORD = "goodPassword";

    private static final String BAD_PASSWORD = "badPassword";

    private static final String BLACKLIST_PASSWORD = "blacklistPassword";

    private static final String NULL_PASSWORD = null;

    private static final String EMPTY_PASSWORD = "";

    private static final String NULL_EMAIL = null;

    private static final String EMPTY_EMAIL = "";


    private static SignUpServiceImpl service;


    @BeforeAll
    static void beforeAll() {
        EmailValidator emailValidator = mock(EmailValidator.class);
        doNothing().when(emailValidator).validate(GOOD_EMAIL);
        doThrow(new IllegalArgumentException("Email is bad")).when(emailValidator).validate(BAD_EMAIL);
        doThrow(new IllegalArgumentException("Email is null")).when(emailValidator).validate(null);
        doThrow(new IllegalArgumentException("Email is empty")).when(emailValidator).validate("");

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        when(passwordValidator.validate(GOOD_PASSWORD)).thenReturn(true);
        when(passwordValidator.validate(BAD_PASSWORD)).thenReturn(false);
        when(passwordValidator.validate(NULL_PASSWORD)).thenReturn(false);
        when(passwordValidator.validate(EMPTY_PASSWORD)).thenReturn(false);

        PasswordBlacklistRepository passwordBlacklistRepository = mock(PasswordBlacklistRepository.class);
        when(passwordBlacklistRepository.contains(anyString())).thenReturn(false);
        when(passwordBlacklistRepository.contains(BLACKLIST_PASSWORD)).thenReturn(true);

        service = new SignUpServiceImpl(emailValidator, passwordValidator, passwordBlacklistRepository);
    }

    @Test
    void testSignUpWithGoodCredentials() {
        assertDoesNotThrow(() -> service.signUp(GOOD_EMAIL, GOOD_PASSWORD));
    }

    @Test
    void testSignUpWithBadEmailAndGoodPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, GOOD_PASSWORD));
        assertEquals("Email is bad", exception.getMessage());
    }

    @Test
    void testSignUpWithGoodEmailAndBadPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, BAD_PASSWORD));
        assertEquals("Password incorrect", exception.getMessage());
    }

    @Test
    void testSignUpWithGoodEmailAndBlacklistPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, BLACKLIST_PASSWORD));
        assertEquals("Password is known", exception.getMessage());
    }

    @Test
    void testSignUpWithBadEmailAndBadPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, BAD_PASSWORD));
        assertEquals("Email is bad", exception.getMessage());
    }

    @Test
    void testSignUpWithBadEmailAndBlacklistPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, BLACKLIST_PASSWORD));
        assertEquals("Email is bad", exception.getMessage());
    }

    @Test
    void testSignUpWithNullEmailAndNullPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(NULL_PASSWORD, NULL_PASSWORD));
        assertEquals("Email is null", exception.getMessage());
    }

    @Test
    void testSignUpWithNullEmailAndBadPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(NULL_EMAIL, BAD_PASSWORD));
        assertEquals("Email is null", exception.getMessage());
    }

    @Test
    void testSignUpWithNullEmailAndBlacklistPasswords() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(NULL_EMAIL, BLACKLIST_PASSWORD));
        assertEquals("Email is null", exception.getMessage());
    }

    @Test
    void testSignUpWithNullEmailAndGoodPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(NULL_EMAIL, GOOD_PASSWORD));
        assertEquals("Email is null", exception.getMessage());
    }

    @Test
    void testSignUpWithGoodEmailAndNullPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, NULL_PASSWORD));
        assertEquals("Password incorrect", exception.getMessage());
    }

    @Test
    void testSignUpWithBadEmailAndNullPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, NULL_PASSWORD));
        assertEquals("Email is bad", exception.getMessage());
    }

    @Test
    void testSignUpWithEmptyEmailAndNullPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(EMPTY_EMAIL, NULL_PASSWORD));
        assertEquals("Email is empty", exception.getMessage());
    }

    @Test
    void testSignUpWithEmptyEmailAndBadPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(EMPTY_EMAIL, BAD_PASSWORD));
        assertEquals("Email is empty", exception.getMessage());
    }

    @Test
    void testSignUpWithEmptyEmailAndBlacklistPasswords() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(EMPTY_EMAIL, BLACKLIST_PASSWORD));
        assertEquals("Email is empty", exception.getMessage());
    }

    @Test
    void testSignUpWithEmptyEmailAndGoodPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(EMPTY_EMAIL, GOOD_PASSWORD));
        assertEquals("Email is empty", exception.getMessage());
    }

    @Test
    void testSignUpWithGoodEmailAndEmptyPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, EMPTY_PASSWORD));
        assertEquals("Password incorrect", exception.getMessage());
    }

    @Test
    void testSignUpWithBadEmailAndEmptyPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, EMPTY_PASSWORD));
        assertEquals("Email is bad", exception.getMessage());
    }
}