package mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ClientRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientRowMapperTest {

    private static final Long ID = 1L;

    private static final String FIRST_NAME = "Veronika";

    private static final String LAST_NAME = "Yamancheva";

    private static final String EMAIL = "vhsroni@mail.ru";

    private static final String NICKNAME = "vhsroni";

    private static final String PASSWORD = "HashedPassword123";

    private static final boolean IS_ADMIN = true;

    @Test
    void mapRowValidResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("first_name")).thenReturn(FIRST_NAME);
        when(resultSet.getString("last_name")).thenReturn(LAST_NAME);
        when(resultSet.getString("email")).thenReturn(EMAIL);
        when(resultSet.getString("nickname")).thenReturn(NICKNAME);
        when(resultSet.getString("password")).thenReturn(PASSWORD);
        when(resultSet.getBoolean("is_admin")).thenReturn(IS_ADMIN);

        ClientRowMapper rowMapper = new ClientRowMapper();
        ClientEntity entity = rowMapper.mapRow(resultSet, 0);

        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(FIRST_NAME, entity.getFirstName());
        assertEquals(LAST_NAME, entity.getLastName());
        assertEquals(EMAIL, entity.getEmail());
        assertEquals(NICKNAME, entity.getNickname());
        assertEquals(PASSWORD, entity.getPassword());
        assertEquals(IS_ADMIN, entity.isAdmin());
    }

    @Test
    void mapRowResultSetThrowsException() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenThrow(new SQLException("Database error"));
        ClientRowMapper rowMapper = new ClientRowMapper();
        assertThrows(SQLException.class, () -> rowMapper.mapRow(resultSet, 0));
    }
}