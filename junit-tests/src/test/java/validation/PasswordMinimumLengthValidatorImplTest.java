package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.PasswordMinimumLengthValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;
import static constants.PasswordTestCases.*;

class PasswordMinimumLengthValidatorImplTest {

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