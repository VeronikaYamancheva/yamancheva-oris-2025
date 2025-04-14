package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.vhsroni.api.dto.request.LogoutRequest;
import ru.itis.vhsroni.api.dto.response.OperationResponse;
import ru.itis.vhsroni.entity.UserEntity;
import ru.itis.vhsroni.exceptions.EmptyTokenValidationServiceException;
import ru.itis.vhsroni.exceptions.InvalidTokenServiceException;
import ru.itis.vhsroni.repositories.UserRepository;
import ru.itis.vhsroni.services.impl.BaseLogoutServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BaseLogoutServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BaseLogoutServiceImpl logoutService;

    private static final String VALID_TOKEN = "valid-token";

    private static final String INVALID_TOKEN = "invalid-token";

    private static final LogoutRequest VALID_LOGOUT_REQUEST = new LogoutRequest(VALID_TOKEN);

    private static final LogoutRequest INVALID_LOGOUT_REQUEST = new LogoutRequest(INVALID_TOKEN);

    private static final LogoutRequest EMPTY_LOGOUT_REQUEST = new LogoutRequest("");

    private static final UserEntity USER_WITH_TOKEN = new UserEntity();

    static {
        USER_WITH_TOKEN.setToken(VALID_TOKEN);
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogoutSuccess() {
        when(userRepository.findByToken(VALID_TOKEN)).thenReturn(Optional.of(USER_WITH_TOKEN));
        OperationResponse response = logoutService.logout(VALID_LOGOUT_REQUEST);

        assertEquals(0, response.getOperationStatus());
        assertEquals("успешный выход", response.getDescription());
        assertNull(USER_WITH_TOKEN.getToken());
    }

    @Test
    void testLogoutWithEmptyToken() {
        assertThrows(EmptyTokenValidationServiceException.class, () -> {
            logoutService.logout(EMPTY_LOGOUT_REQUEST);
        });
        verify(userRepository, never()).findByToken(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testLogoutWithInvalidToken(){
        when(userRepository.findByToken(INVALID_TOKEN)).thenReturn(Optional.empty());
        assertThrows(InvalidTokenServiceException.class, () -> {
            logoutService.logout(INVALID_LOGOUT_REQUEST);
        });
        verify(userRepository, never()).save(any());
    }
}
