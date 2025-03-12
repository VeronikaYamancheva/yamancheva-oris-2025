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

    @BeforeAll
    static void beforeAll() {
        EmailValidator emailValidator = mock(EmailValidator.class);
        doNothing().when(emailValidator).validate(anyString());
        doThrow(new IllegalArgumentException("Email too short!")).when(emailValidator).validate("badEmail");

        PasswordValidator passwordValidator = mock(PasswordValidator.class);
        when(passwordValidator.validate(anyString())).thenReturn(true);
        when(passwordValidator.validate("badPassword")).thenReturn(false);

        PasswordBlacklistRepository passwordBlacklistRepository = mock(PasswordBlacklistRepository.class);
        when(passwordBlacklistRepository.contains(anyString())).thenReturn(false);
        when(passwordBlacklistRepository.contains("blacklistPassword")).thenReturn(true);

        service = new SignUpServiceImpl(emailValidator, passwordValidator, passwordBlacklistRepository);
    }

    @Test
    void testGoodCredentials() {
        assertDoesNotThrow(() -> service.signUp("goodEmail", "goodPassword"));
    }

    @Test
    void testBadEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("badEmail", "goodPassword"));
    }

    @Test
    void testBadPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("goodEmail", "badPassword"),
                "Password incorrect");
    }

    @Test
    void testBlacklistPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> service.signUp("goodEmail", "blacklistPassword"),
                "Password is known");
    }
}