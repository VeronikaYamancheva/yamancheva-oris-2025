package validators;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.PasswordValidator;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private static final String VALID_PASSWORD = "Password123";

    private static final String PASSWORD_WITHOUT_UPPERCASE = "password123";

    private static final String PASSWORD_WITHOUT_LOWERCASE = "PASSWORD123";

    private static final String PASSWORD_WITHOUT_DIGIT = "Password";

    private static final String SHORT_PASSWORD = "Pas1";

    private static final String EMPTY_PASSWORD = "";

    @Test
    void testValidPassword() {
        boolean isValid = PasswordValidator.checkPassword(VALID_PASSWORD);
        assertTrue(isValid);
    }

    @Test
    void testPasswordWithoutUppercase() {
        boolean isValid = PasswordValidator.checkPassword(PASSWORD_WITHOUT_UPPERCASE);
        assertFalse(isValid);
    }

    @Test
    void testPasswordWithoutLowercase() {
        boolean isValid = PasswordValidator.checkPassword(PASSWORD_WITHOUT_LOWERCASE);
        assertFalse(isValid);
    }

    @Test
    void testPasswordWithoutDigit() {
        boolean isValid = PasswordValidator.checkPassword(PASSWORD_WITHOUT_DIGIT);
        assertFalse(isValid);
    }

    @Test
    void testShortPassword() {
        boolean isValid = PasswordValidator.checkPassword(SHORT_PASSWORD);
        assertFalse(isValid);
    }

    @Test
    void testEmptyPassword() {
        boolean isValid = PasswordValidator.checkPassword(EMPTY_PASSWORD);
        assertFalse(isValid);
    }
}