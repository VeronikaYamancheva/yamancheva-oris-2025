package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.PasswordCaseValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;
import static constants.PasswordTestCases.*;

class PasswordCaseValidatorImplTest {

    private static PasswordCaseValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new PasswordCaseValidatorImpl(2, 2);
    }

    @Test
    void testUpperAndLowerCorrectPassword() {
        boolean result = validator.validate(UPPER_CORRECT_LOWER_CORRECT_PASSWORD);
        assertTrue(result);
    }

    @Test
    void testUpperAndLowerMiddlePassword() {
        boolean result = validator.validate(UPPER_MIDDLE_LOWER_MIDDLE_PASSWORD);
        assertTrue(result);
    }

    @Test
    void testUpperAndLowerWrongPassword() {
        boolean result = validator.validate(UPPER_WRONG_LOWER_WRONG_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testNoLettersPassword() {
        boolean result = validator.validate(ONLY_DIGITS_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testNullPassword() {
        boolean result = validator.validate(NULL_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testUpperCorrectLowerWrongPassword() {
        boolean result = validator.validate(UPPER_CORRECT_LOWER_WRONG_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testUpperCorrectLowerMiddlePassword() {
        boolean result = validator.validate(UPPER_CORRECT_LOWER_MIDDLE_PASSWORD);
        assertTrue(result);
    }

    @Test
    void testUpperMiddleLowerCorrectPassword() {
        boolean result = validator.validate(UPPER_MIDDLE_LOWER_CORRECT_PASSWORD);
        assertTrue(result);
    }

    @Test
    void testUpperMiddleLowerWrongPassword() {
        boolean result = validator.validate(UPPER_MIDDLE_LOWER_WRONG_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testUpperWrongLowerCorrectPassword() {
        boolean result = validator.validate(UPPER_WRONG_LOWER_CORRECT_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testUpperWrongLowerMiddlePassword() {
        boolean result = validator.validate(UPPER_WRONG_LOWER_MIDDLE_PASSWORD);
        assertFalse(result);
    }

    @Test
    void testEmptyPassword() {
        boolean result = validator.validate(EMPTY_PASSWORD);
        assertFalse(result);
    }
}