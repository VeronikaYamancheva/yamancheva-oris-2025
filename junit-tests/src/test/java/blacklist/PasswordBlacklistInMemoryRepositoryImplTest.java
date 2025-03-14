package blacklist;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.blacklist.impl.PasswordBlacklistInMemoryRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

class PasswordBlacklistInMemoryRepositoryImplTest {

    private static PasswordBlacklistInMemoryRepositoryImpl passwordBlacklistRepository;

    private static final String KNOWN_PASSWORD = "qwerty007";

    private static final String UNKNOWN_PASSWORD = "somePassword";

    private static final String NULL_PASSWORD = null;

    private static final String EMPTY_PASSWORD = "";

    @BeforeAll
    static void beforeAll() {
        passwordBlacklistRepository = new PasswordBlacklistInMemoryRepositoryImpl();
    }

    @Test
    void testBlacklistPassword() {
        assertTrue(passwordBlacklistRepository.contains(KNOWN_PASSWORD));
    }

    @Test
    void testNonBlacklistPassword() {
        assertFalse(passwordBlacklistRepository.contains(UNKNOWN_PASSWORD));
    }

    @Test
    void testNullPassword() {
        assertFalse(passwordBlacklistRepository.contains(NULL_PASSWORD));
    }

    @Test
    void testEmptyPassword() {
        assertFalse(passwordBlacklistRepository.contains(EMPTY_PASSWORD));
    }
}
