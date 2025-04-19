package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.ClientRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.ClientServiceImpl;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.SignInServiceImpl;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.HashUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SignInServiceImplTest {

    private static final Long ID = 1L;

    private static final String EMAIL = "vhsroni@mail.ru";

    private static final String NICKNAME = "vhsroni";

    private static final String PASSWORD = "HashedPassword123";

    private static final boolean IS_ADMIN = true;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private SignInServiceImpl signInService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthorizeWithValidCredentials() {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(ID)
                .email(EMAIL)
                .nickname(NICKNAME)
                .password(HashUtil.hashPassword(PASSWORD))
                .isAdmin(IS_ADMIN)
                .build();
        when(clientRepository.findByNickname(NICKNAME)).thenReturn(Optional.of(clientEntity));
        ClientSignResponseDto response = signInService.authorize(NICKNAME, PASSWORD);
        assertTrue(response.getClient().isPresent());
        assertEquals(clientEntity, response.getClient().get());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void testAuthorizeWhenNicknameDoesNotExist() {
        when(clientRepository.findByNickname(NICKNAME)).thenReturn(Optional.empty());
        ClientSignResponseDto response = signInService.authorize(NICKNAME, PASSWORD);
        assertFalse(response.getClient().isPresent());
        assertTrue(response.getErrors().contains("There is no such client with this nickname."));
    }

    @Test
    void testAuthorizeWhenPasswordIsIncorrect() {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(ID)
                .email(EMAIL)
                .nickname(NICKNAME)
                .password(HashUtil.hashPassword(PASSWORD))
                .isAdmin(IS_ADMIN)
                .build();
        when(clientRepository.findByNickname(NICKNAME)).thenReturn(Optional.of(clientEntity));
        ClientSignResponseDto response = signInService.authorize(NICKNAME, PASSWORD);
        assertTrue(response.getClient().isPresent());
        assertFalse(response.getErrors().contains("Password don't match."));
    }
}
