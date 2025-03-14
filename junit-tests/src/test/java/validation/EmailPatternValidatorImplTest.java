package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.EmailPatternValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;
import static constants.EmailTestCases.*;

class EmailPatternValidatorImplTest {

    private static EmailPatternValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new EmailPatternValidatorImpl();
    }

    @Test
    void testMatchingPatternEmail() {
        assertDoesNotThrow(() -> validator.validate(CORRECT_PATTERN_EMAIL));
    }

    @Test
    void testMismatchingPatternEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(WRONG_PATTERN_EMAIL),
                "Email doesn't match pattern!");
    }

    @Test
    void testNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(NULL_EMAIL),
                "Email is null");
    }

    @Test
    void testEmptyEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(EMPTY_EMAIL),
                "Email is empty");
    }

    @Test
    void testBlankEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(BLANK_EMAIL),
                "Email is empty");
    }
}
