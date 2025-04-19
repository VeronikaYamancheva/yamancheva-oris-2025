package validators;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.EmailValidator;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private static final String VALID_EMAIL = "test@example.com";

    private static final String EMAIL_WITHOUT_SYMBOL = "testexample.com";

    private static final String EMAIL_WITHOUT_DOMAIN = "test@";

    private static final String EMAIL_WITH_SPACE = "test@exa mple.com";

    private static final String EMAIL_WITH_INVALID_DOMAIN = "test@example.c";

    private static final String EMPTY_EMAIL = "";

    @Test
    void testValidEmail() {
        boolean isValid = EmailValidator.checkEmail(VALID_EMAIL);
        assertTrue(isValid);
    }

    @Test
    void testEmailWithoutSymbol() {
        boolean isValid = EmailValidator.checkEmail(EMAIL_WITHOUT_SYMBOL);
        assertFalse(isValid);
    }

    @Test
    void testEmailWithoutDomain() {
        boolean isValid = EmailValidator.checkEmail(EMAIL_WITHOUT_DOMAIN);
        assertFalse(isValid);
    }

    @Test
    void testEmailWithSpace() {
        boolean isValid = EmailValidator.checkEmail(EMAIL_WITH_SPACE);
        assertFalse(isValid);
    }

    @Test
    void testEmailWithInvalidDomain() {
        boolean isValid = EmailValidator.checkEmail(EMAIL_WITH_INVALID_DOMAIN);
        assertFalse(isValid);
    }

    @Test
    void testEmptyEmail() {
        boolean isValid = EmailValidator.checkEmail(EMPTY_EMAIL);
        assertFalse(isValid);
    }
}