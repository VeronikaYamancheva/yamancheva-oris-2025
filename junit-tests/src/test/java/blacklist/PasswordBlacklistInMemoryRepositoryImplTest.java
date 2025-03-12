package blacklist;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.blacklist.impl.PasswordBlacklistInMemoryRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

class PasswordBlacklistInMemoryRepositoryImplTest {

    private static PasswordBlacklistInMemoryRepositoryImpl passwordBlacklistRepository;

    @BeforeAll
    static void beforeAll() {
        passwordBlacklistRepository = new PasswordBlacklistInMemoryRepositoryImpl();
    }

    @Test
    void testBlacklistPassword() {
        assertTrue(passwordBlacklistRepository.contains("qwerty007"));
    }

    @Test
    void testNonBlacklistPassword() {
        assertFalse(passwordBlacklistRepository.contains("somePassword"));
    }

    @Test
    void testNullPassword() {
        assertFalse(passwordBlacklistRepository.contains(null));
    }

    @Test
    void testEmptyPassword() {
        assertFalse(passwordBlacklistRepository.contains(""));
    }
}
