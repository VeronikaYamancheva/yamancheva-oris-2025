package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.EmailPatternValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class EmailPatternValidatorImplTest {

    private static EmailPatternValidatorImpl validator;

    @BeforeAll
    static void beforeAll() {
        validator = new EmailPatternValidatorImpl();
    }

    @Test
    void testMatchingPatternEmail() {
        assertDoesNotThrow(() -> validator.validate("testemail@mail.ru"));
    }

    @Test
    void testMismatchingPatternEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate("12345"),
                "Email doesn't match pattern!");
    }

    @Test
    void testNullEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(null),
                "Email is null");
    }

    @Test
    void testEmptyEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(""),
                "Email is empty");
    }

    @Test
    void testBlankEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate("  "),
                "Email is empty");
    }
}
