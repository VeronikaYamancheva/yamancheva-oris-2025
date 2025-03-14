package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.EmailMinimumLengthValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class EmailMinimumLengthValidatorImplTest {

    private static final String NULL_EMAIL = null;

    private static final String EMPTY_EMAIL = "";

    private static final String BLANK_EMAIL = " ";

    private static final String CORRECT_EMAIL = "testemail@mail.ru";

    private static final String WRONG_EMAIL = "000";

    private static final String MIDDLE_EMAIL = "12345";

    private static EmailMinimumLengthValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new EmailMinimumLengthValidatorImpl(5);
    }

    @Test
    void testLongEmail() {
        assertDoesNotThrow(() -> validator.validate("longEmail"));
    }

    @Test
    void testShortEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(WRONG_EMAIL),
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
        assertDoesNotThrow(() -> validator.validate(MIDDLE_EMAIL));
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
