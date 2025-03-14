package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.PasswordCaseValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class PasswordCaseValidatorImplTest {

    private static final String UPPER_CORRECT_LOWER_CORRECT_PASSWORD = "AaBbCc";
    private static final String UPPER_MIDDLE_LOWER_MIDDLE_PASSWORD = "AaBb";
    private static final String UPPER_WRONG_LOWER_WRONG_PASSWORD = "Aa";
    private static final String NO_LETTERS_PASSWORD = "123";
    private static final String NULL_PASSWORD = null;
    private static final String UPPER_CORRECT_LOWER_WRONG_PASSWORD = "ABCa";
    private static final String UPPER_CORRECT_LOWER_MIDDLE_PASSWORD = "ABCab";
    private static final String UPPER_MIDDLE_LOWER_CORRECT_PASSWORD = "ABabc";
    private static final String UPPER_MIDDLE_LOWER_WRONG_PASSWORD = "ABa";
    private static final String UPPER_WRONG_LOWER_CORRECT_PASSWORD = "Aabc";
    private static final String UPPER_WRONG_LOWER_MIDDLE_PASSWORD = "Aab";
    private static final String EMPTY_PASSWORD = "";

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
        boolean result = validator.validate(NO_LETTERS_PASSWORD);
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