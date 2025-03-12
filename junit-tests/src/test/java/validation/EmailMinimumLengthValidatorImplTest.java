package validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.validation.impl.EmailMinimumLengthValidatorImpl;

import static org.junit.jupiter.api.Assertions.*;

class EmailMinimumLengthValidatorImplTest {

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
                () -> validator.validate("000"),
                "Email too short!");
    }

    @Test
    void testNullEmail(){
        assertThrows(IllegalArgumentException.class,
                () -> validator.validate(null),
                "Email is null");
    }

    @Test
    void testMiddleEmail() {
        assertDoesNotThrow(() -> validator.validate("12345"));
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
