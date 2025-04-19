package validators;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.UsernameValidator;

import static org.junit.jupiter.api.Assertions.*;

class UsernameValidatorTest {

    private static final String ENGLISH_NAME = "Ivan";

    private static final String RUSSIAN_NAME = "Иван";

    private static final String NAME_WITH_DIGITS = "Ivan123";

    private static final String NAME_WITH_SPECIAL_CHARACTERS = "Ivan_Ivanov";

    private static final String NAME_WITH_SPACE = "Ivan Ivanov";

    private static final String EMPTY_NAME = "";

    @Test
    void testEnglishName() {
        boolean isValid = UsernameValidator.checkName(ENGLISH_NAME);
        assertTrue(isValid);
    }

    @Test
    void testRussianName() {
        boolean isValid = UsernameValidator.checkName(RUSSIAN_NAME);
        assertTrue(isValid);
    }

    @Test
    void testNameWithDigits() {
        boolean isValid = UsernameValidator.checkName(NAME_WITH_DIGITS);
        assertFalse(isValid);
    }

    @Test
    void testNameWithSpecialCharacters() {
        boolean isValid = UsernameValidator.checkName(NAME_WITH_SPECIAL_CHARACTERS);
        assertFalse(isValid);
    }

    @Test
    void testNameWithSpaces() {
        boolean isValid = UsernameValidator.checkName(NAME_WITH_SPACE);
        assertFalse(isValid);
    }

    @Test
    void testEmptyName() {
        boolean isValid = UsernameValidator.checkName(EMPTY_NAME);
        assertFalse(isValid);
    }
}