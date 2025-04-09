package services;

import org.junit.jupiter.api.Test;
import ru.itis.vhsroni.services.impl.BaseTokenServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class BaseTokenServiceImplTest {

    private final BaseTokenServiceImpl tokenService = new BaseTokenServiceImpl();

    @Test
    void testGenerateTokenValid() {
        String token = tokenService.generateToken();

        assertNotNull(token);
        assertTrue(token.startsWith("new-generated-token-"));

        String uuidPart = token.substring("new-generated-token-".length());
        assertDoesNotThrow(() -> UUID.fromString(uuidPart));
    }

    @Test
    void testGenerateTokenUnique() {
        String token1 = tokenService.generateToken();
        String token2 = tokenService.generateToken();

        assertNotEquals(token1, token2);
    }
}
