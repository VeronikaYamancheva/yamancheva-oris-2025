package blacklist;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.blacklist.impl.PasswordBlacklistInMemoryRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;
import static constants.PasswordTestCases.*;

class PasswordBlacklistInMemoryRepositoryImplTest {

    private static PasswordBlacklistInMemoryRepositoryImpl passwordBlacklistRepository;


    @BeforeAll
    static void beforeAll() {
        passwordBlacklistRepository = new PasswordBlacklistInMemoryRepositoryImpl();
    }

    @Test
    void testBlacklistPassword() {
        assertTrue(passwordBlacklistRepository.contains(BLACKLIST_KNOWN_PASSWORD));
    }

    @Test
    void testNonBlacklistPassword() {
        assertFalse(passwordBlacklistRepository.contains(BLACKLIST_UNKNOWN_PASSWORD));
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
