package validators;

import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.exceptions.EmptyEmailValidationServiceException;
import ru.itis.vhsroni.exceptions.IncorrectEmailValidationServiceException;
import ru.itis.vhsroni.validators.impl.EmailFormatValidator;

import static org.junit.jupiter.api.Assertions.*;

class EmailFormatValidatorTest {

    private final EmailFormatValidator validator = new EmailFormatValidator();

    private static final String VALID_EMAIL_SIMPLE = "test@example.com";
    private static final String VALID_EMAIL_WITH_DOTS = "first.last@example.com";
    private static final String VALID_EMAIL_WITH_PLUS = "first+last@example.com";
    private static final String VALID_EMAIL_WITH_NUMBERS = "12345@example.com";
    private static final String VALID_EMAIL_WITH_HYPHEN = "first-last@example.com";
    private static final String VALID_EMAIL_WITH_SUBDOMAINS = "email@sub.example.co.uk";
    private static final String NULL_EMAIL = null;
    private static final String EMPTY_EMAIL = "";
    private static final String BLANK_EMAIL = "   ";
    private static final String EMAIL_WITHOUT_AT = "plainaddress";
    private static final String EMAIL_WITHOUT_DOMAIN = "email@";
    private static final String EMAIL_WITH_INVALID_CHARS = "email@example..com";
    private static final String EMAIL_WITH_SPACES = "email @example.com";
    private static final String EMAIL_WITH_INVALID_TLD = "email@example.c";

    @Test
    void testCheckValidNullEmail() {
        assertThrows(EmptyEmailValidationServiceException.class,
                () -> validator.checkValid(NULL_EMAIL));
    }

    @Test
    void testCheckValidEmptyEmail() {
        assertThrows(EmptyEmailValidationServiceException.class,
                () -> validator.checkValid(EMPTY_EMAIL));
    }

    @Test
    void testCheckValidBlankEmail() {
        assertThrows(EmptyEmailValidationServiceException.class,
                () -> validator.checkValid(BLANK_EMAIL));
    }

    @Test
    void testCheckValidEmailWithoutAt() {
        assertThrows(IncorrectEmailValidationServiceException.class,
                () -> validator.checkValid(EMAIL_WITHOUT_AT));
    }

    @Test
    void testCheckValidEmailWithoutDomain() {
        assertThrows(IncorrectEmailValidationServiceException.class,
                () -> validator.checkValid(EMAIL_WITHOUT_DOMAIN));
    }

    @Test
    void testCheckValidEmailWithInvalidChars() {
        assertThrows(IncorrectEmailValidationServiceException.class,
                () -> validator.checkValid(EMAIL_WITH_INVALID_CHARS));
    }

    @Test
    void testCheckValidEmailWithSpaces() {
        assertThrows(IncorrectEmailValidationServiceException.class,
                () -> validator.checkValid(EMAIL_WITH_SPACES));
    }

    @Test
    void testCheckValidEmailWithInvalidTld() {
        assertThrows(IncorrectEmailValidationServiceException.class,
                () -> validator.checkValid(EMAIL_WITH_INVALID_TLD));
    }

    @Test
    void testCheckValidSimpleEmail() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_EMAIL_SIMPLE));
    }

    @Test
    void testCheckValidEmailWithDots() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_EMAIL_WITH_DOTS));
    }

    @Test
    void testCheckValidEmailWithPlus() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_EMAIL_WITH_PLUS));
    }

    @Test
    void testCheckValidEmailWithNumbers() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_EMAIL_WITH_NUMBERS));
    }

    @Test
    void testCheckValidEmailWithHyphen() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_EMAIL_WITH_HYPHEN));
    }

    @Test
    void testCheckValidEmailWithSubdomains() {
        assertDoesNotThrow(() -> validator.checkValid(VALID_EMAIL_WITH_SUBDOMAINS));
    }
}
