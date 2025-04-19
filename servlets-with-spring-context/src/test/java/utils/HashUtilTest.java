package utils;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.HashUtil;

import static org.junit.jupiter.api.Assertions.*;

class HashUtilTest {

    private static final String RAW_PASSWORD = "mySecurePassword123";

    private static final String WRONG_PASSWORD = "wrongPassword";

    private static final String EMPTY_PASSWORD = "";

    @Test
    void testVerifyPasswordWithCorrectPassword() {
        String hashedPassword = HashUtil.hashPassword(RAW_PASSWORD);
        assertTrue(HashUtil.verifyPassword(RAW_PASSWORD, hashedPassword));
    }

    @Test
    void testVerifyPasswordWithIncorrectPassword() {
        String hashedPassword = HashUtil.hashPassword(RAW_PASSWORD);
        assertFalse(HashUtil.verifyPassword(WRONG_PASSWORD, hashedPassword));
    }

    @Test
    void testVerifyPasswordWithEmptyPassword() {
        String hashedPassword = HashUtil.hashPassword(EMPTY_PASSWORD);

        assertTrue(HashUtil.verifyPassword(EMPTY_PASSWORD, hashedPassword));
    }

    @Test
    void testVerifyPasswordWithNullHash() {
        assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword(RAW_PASSWORD, null));
    }
}
