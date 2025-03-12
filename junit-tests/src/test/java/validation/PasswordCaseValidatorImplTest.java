package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.PasswordCaseValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class PasswordCaseValidatorImplTest {

    private static PasswordCaseValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new PasswordCaseValidatorImpl(2, 2);
    }

    @Test
    void testUpperAndLowerCorrectPassword() {
        boolean result = validator.validate("AaBbCc");
        assertTrue(result);
    }

    @Test
    void testUpperAndLowerMiddlePassword() {
        boolean result = validator.validate("AaBb");
        assertTrue(result);
    }

    @Test
    void testUpperAndLowerWrongPassword() {
        boolean result = validator.validate("Aa");
        assertFalse(result);
    }

    @Test
    void testNoLettersPassword() {
        boolean result = validator.validate("123");
        assertFalse(result);
    }

    @Test
    void testNullPassword() {
        boolean result = validator.validate(null);
        assertFalse(result);
    }

    @Test
    void testUpperCorrectLowerWrongPassword() {
        boolean result = validator.validate("ABCa");
        assertFalse(result);
    }

    @Test
    void testUpperCorrectLowerMiddlePassword() {
        boolean result = validator.validate("ABCab");
        assertTrue(result);
    }

    @Test
    void testUpperMiddleLowerCorrectPassword() {
        boolean result = validator.validate("ABabc");
        assertTrue(result);
    }

    @Test
    void testUpperMiddleLowerWrongPassword() {
        boolean result = validator.validate("ABa");
        assertFalse(result);
    }

    @Test
    void testUpperWrongLowerCorrectPassword() {
        boolean result = validator.validate("Aabc");
        assertFalse(result);
    }

    @Test
    void testUpperWrongLowerMiddlePassword() {
        boolean result = validator.validate("Aab");
        assertFalse(result);
    }

    @Test
    void testEmptyPassword() {
        boolean result = validator.validate("");
        assertFalse(result);
    }


}
