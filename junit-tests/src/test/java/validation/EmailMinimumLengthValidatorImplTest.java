package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.EmailMinimumLengthValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;
import static constants.EmailTestCases.*;

class EmailMinimumLengthValidatorImplTest {

    private static EmailMinimumLengthValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new EmailMinimumLengthValidatorImpl(5);
    }

    @Test
    void testLongEmail() {
        assertDoesNotThrow(() -> validator.validate(CORRECT_LENGTH_EMAIL));
    }

    @Test
    void testShortEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(WRONG_LENGTH_EMAIL),
                "Email too short!");
    }

    @Test
    void testNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(NULL_EMAIL),
                "Email is null");
    }

    @Test
    void testMiddleEmail() {
        assertDoesNotThrow(() -> validator.validate(MIDDLE_LENGTH_EMAIL));
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
