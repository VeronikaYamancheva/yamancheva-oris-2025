package validators;

import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.exceptions.EmptyPasswordValidationServiceException;
import ru.itis.vhsroni.exceptions.IncorrectPasswordValidationServiceException;
import ru.itis.vhsroni.validators.impl.PasswordStrengthValidator;

import static org.junit.jupiter.api.Assertions.*;

class PasswordStrengthValidatorTest {

    private final PasswordStrengthValidator validator = new PasswordStrengthValidator();

    private static final String NULL_PASSWORD = null;
    private static final String EMPTY_PASSWORD = "";
    private static final String BLANK_PASSWORD = "   ";
    private static final String SHORT_PASSWORD = "A1b!c";
    private static final String NO_UPPER_CASE = "abc123!@#";
    private static final String NO_LOWER_CASE = "ABC123!@#";
    private static final String NO_DIGITS = "Abcdef!@#";
    private static final String NO_SPECIAL_CHARS = "Abc123456";
    private static final String VALID_PASSWORD = "ValidPass123!";

    @Test
    void testCheckValidNullPassword() {
        assertThrows(EmptyPasswordValidationServiceException.class,
                () -> validator.checkValid(NULL_PASSWORD));
    }

    @Test
    void testCheckValidEmptyPassword() {
        assertThrows(EmptyPasswordValidationServiceException.class,
                () -> validator.checkValid(EMPTY_PASSWORD));
    }

    @Test
    void testCheckValidBlankPassword() {
        assertThrows(EmptyPasswordValidationServiceException.class,
                () -> validator.checkValid(BLANK_PASSWORD));
    }

    @Test
    void testCheckValidShortPassword() {
        assertThrows(IncorrectPasswordValidationServiceException.class,
                () -> validator.checkValid(SHORT_PASSWORD));
    }

    @Test
    void testCheckValidNoUpperCase() {
        assertThrows(IncorrectPasswordValidationServiceException.class,
                () -> validator.checkValid(NO_UPPER_CASE));
    }

    @Test
    void testCheckValidNoLowerCase() {
        assertThrows(IncorrectPasswordValidationServiceException.class,
                () -> validator.checkValid(NO_LOWER_CASE));
    }

    @Test
    void testCheckValidNoDigits() {
        assertThrows(IncorrectPasswordValidationServiceException.class,
                () -> validator.checkValid(NO_DIGITS));
    }

    @Test
    void testCheckValidNoSpecialChars() {
        assertThrows(IncorrectPasswordValidationServiceException.class,
                () -> validator.checkValid(NO_SPECIAL_CHARS));
    }

    @Test
    void testCheckValidSuccess() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_PASSWORD));
    }
}
