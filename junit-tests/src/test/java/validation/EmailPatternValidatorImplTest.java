package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.EmailPatternValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class EmailPatternValidatorImplTest {

    private static final String NULL_EMAIL = null;

    private static final String EMPTY_EMAIL = "";

    private static final String BLANK_EMAIL = " ";

    private static final String CORRECT_EMAIL = "testemail@mail.ru";

    private static final String WRONG_EMAIL = "12345";

    private static EmailPatternValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new EmailPatternValidatorImpl();
    }

    @Test
    void testMatchingPatternEmail() {
        assertDoesNotThrow(() -> validator.validate(CORRECT_EMAIL));
    }

    @Test
    void testMismatchingPatternEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(WRONG_EMAIL),
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
