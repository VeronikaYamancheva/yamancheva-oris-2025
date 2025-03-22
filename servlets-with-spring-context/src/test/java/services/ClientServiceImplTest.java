package services;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.repository.ClientRepository;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.ClientServiceImpl;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private static final Long ID = 1L;

    private static final String FIRST_NAME = "Veronika";

    private static final String LAST_NAME = "Yamancheva";

    private static final String EMAIL = "vhsroni@mail.ru";

    private static final String NICKNAME = "vhsroni";

    private static final String PASSWORD = "HashedPassword123";

    private static final boolean IS_ADMIN = true;

    private static final String ADMIN_CODE = "1234";

    private static final String WRONG_EMAIL = "email";

    private static final String WRONG_PASSWORD = "Password1";

    private static final String EXISTING_NICKNAME = "ExistingNickname";

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUpdateWithValidInput() throws DbException {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .nickname(NICKNAME)
                .password(PASSWORD)
                .isAdmin(IS_ADMIN)
                .build();
        when(clientRepository.update(FIRST_NAME, LAST_NAME, EMAIL)).thenReturn(Optional.of(clientEntity));
        ClientSignResponseDto response = clientService.update(FIRST_NAME, LAST_NAME, EMAIL);
        assertTrue(response.getClient().isPresent());
        assertEquals(clientEntity, response.getClient().get());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void testUpdateWhenFirstNameIsInvalid() throws DbException {
        ClientSignResponseDto response = clientService.update("", LAST_NAME, EMAIL);
        assertFalse(response.getClient().isPresent());
        assertTrue(response.getErrors().contains("FirstName is not valid."));
    }

    @Test
    void testCheckUniqueEmailWithUniqueEmail() {
        when(clientRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        boolean isUnique = clientService.checkUniqueEmail(EMAIL);
        assertTrue(isUnique);
    }

    @Test
    void testCheckUniqueEmailWithUExistingEmail() {
        when(clientRepository.findByEmail(EMAIL)).thenReturn(Optional.of(new ClientEntity()));
        boolean isUnique = clientService.checkUniqueEmail(EMAIL);
        assertFalse(isUnique);
    }

    @Test
    void testCheckUniqueNicknameWithUniqueNickname() {
        when(clientRepository.findByNickname(NICKNAME)).thenReturn(Optional.empty());
        boolean isUnique = clientService.checkUniqueNickname(NICKNAME);
        assertTrue(isUnique);
    }

    @Test
    void testCheckUniqueNicknameWithExistingNickname() {
        when(clientRepository.findByNickname(NICKNAME)).thenReturn(Optional.of(new ClientEntity()));
        boolean isUnique = clientService.checkUniqueNickname(NICKNAME);
        assertFalse(isUnique);
    }

    @Test
    void testGetAllNotAdmins() {
        List<ClientEntity> clients = Arrays.asList(
                ClientEntity.builder()
                        .id(ID)
                        .email(EMAIL)
                        .nickname(NICKNAME)
                        .password(PASSWORD)
                        .isAdmin(IS_ADMIN)
                        .build()
        );
        when(clientRepository.getAllNotAdmins()).thenReturn(clients);
        List<ClientEntity> result = clientService.getAllNotAdmins();
        assertEquals(clients, result);
    }

    @Test
    void testSaveEmailIsInvalid() throws IOException, DbException, ServletException {
        ClientSignResponseDto response = clientService.save(WRONG_EMAIL, NICKNAME, PASSWORD, PASSWORD, ADMIN_CODE);
        assertTrue(response.getClient().isEmpty());
        assertTrue(response.getErrors().contains("Email is not valid."));
    }

    @Test
    void testSavePasswordsDoNotMatch() throws IOException, DbException, ServletException {
        ClientSignResponseDto response = clientService.save(EMAIL, NICKNAME, PASSWORD, WRONG_PASSWORD, ADMIN_CODE);
        assertTrue(response.getClient().isEmpty());
        assertTrue(response.getErrors().contains("Passwords don't match"));
    }

    @Test
    void testSaveNicknameExists() throws IOException, DbException, ServletException {
        when(clientRepository.findByNickname(EXISTING_NICKNAME)).thenReturn(Optional.of(new ClientEntity()));
        ClientSignResponseDto response = clientService.save(EMAIL, EXISTING_NICKNAME, PASSWORD, PASSWORD, ADMIN_CODE);
        assertTrue(response.getClient().isEmpty());
        assertTrue(response.getErrors().contains("Nickname exists"));
    }
}