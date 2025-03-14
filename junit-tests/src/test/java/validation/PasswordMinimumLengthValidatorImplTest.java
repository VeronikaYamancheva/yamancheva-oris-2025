package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.PasswordMinimumLengthValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class PasswordMinimumLengthValidatorImplTest {

    private static final String LONG_PASSWORD = "longPassword";
    private static final String SHORT_PASSWORD = "000";
    private static final String MIDDLE_PASSWORD = "12345";
    private static final String BLANK_PASSWORD = "          ";
    private static final String NULL_PASSWORD = null;
    private static final String EMPTY_PASSWORD = "";

    private static PasswordMinimumLengthValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new PasswordMinimumLengthValidatorImpl(5);
    }

    @Test
    void testLongPassword() {
        boolean result = validator.validate(LONG_PASSWORD);
        assertTrue(result);
    }

    @Test
    void testShortPassword() {
        boolean result = validator.validate(SHORT_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testNullPassword() {
        boolean result = validator.validate(NULL_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testMiddlePassword() {
        boolean result = validator.validate(MIDDLE_PASSWORD);
        assertTrue(result);
    }

    @Test
    void testEmptyPassword() {
        boolean result = validator.validate(EMPTY_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testBlankPassword() {
        boolean result = validator.validate(BLANK_PASSWORD);
        assertFalse(result);
    }
}