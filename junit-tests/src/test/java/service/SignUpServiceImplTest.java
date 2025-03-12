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


    private static SignUpServiceImpl service;

    private static final String GOOD_EMAIL = "goodEmail";

    private static final String BAD_EMAIL = "badEmail";

    private static final String GOOD_PASSWORD = "goodPassword";

    private static final String BAD_PASSWORD = "badPassword";

    private static final String BLACKLIST_PASSWORD = "blacklistPassword";

    @BeforeAll
    static void beforeAll() {
        EmailValidator emailValidator = mock(EmailValidator.class);
        doNothing().when(emailValidator).validate(GOOD_EMAIL);
        doThrow(new IllegalArgumentException("Email is bad")).when(emailValidator).validate(BAD_EMAIL);
        doThrow(new IllegalArgumentException("Email is bad")).when(emailValidator).validate(null);
        doThrow(new IllegalArgumentException("Email is bad")).when(emailValidator).validate("");

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        when(passwordValidator.validate(GOOD_PASSWORD)).thenReturn(true);
        when(passwordValidator.validate(BAD_PASSWORD)).thenReturn(false);
        when(passwordValidator.validate(null)).thenReturn(false);
        when(passwordValidator.validate("")).thenReturn(false);

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
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, GOOD_PASSWORD));
    }

    @Test
    void testSignUpWithGoodEmailAndBadPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, BAD_PASSWORD),
                "Password incorrect");
    }

    @Test
    void testSignUpWithGoodEmailAndBlacklistPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, BLACKLIST_PASSWORD),
                "Password is known");
    }

    @Test
    void testSignUpWithBadEmailAndBadPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, BAD_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithBadEmailAndBlacklistPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, BAD_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithNullEmailAndNullPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(null, null),
                "Email is bad");
    }

    @Test
    void testSignUpWithNullEmailAndBadPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(null, BAD_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithNullEmailAndBlacklistPasswords() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(null, BLACKLIST_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithNullEmailAndGoodPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(null, GOOD_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithGoodEmailAndNullPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, null),
                "Password incorrect");
    }

    @Test
    void testSignUpWithBadEmailAndNullPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, null),
                "Email is bad");
    }

    @Test
    void testSignUpWithEmptyEmailAndNullPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("", null),
                "Email is bad");
    }

    @Test
    void testSignUpWithEmptyEmailAndBadPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("", BAD_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithEmptyEmailAndBlacklistPasswords() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("", BLACKLIST_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithEmptyEmailAndGoodPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("", GOOD_PASSWORD),
                "Email is bad");
    }

    @Test
    void testSignUpWithGoodEmailAndEmptyPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(GOOD_EMAIL, ""),
                "Password incorrect");
    }

    @Test
    void testSignUpWithBadEmailAndEmptyPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp(BAD_EMAIL, ""),
                "Email is bad");
    }
}