package mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.DentistRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DentistRowMapperTest {

    private static final Long ID = 1L;

    private static final String FIRST_NAME = "Veronika";

    private static final String LAST_NAME = "Yamancheva";

    private static final String EMAIL = "vhsroni@mail.ru";

    @Test
    void mapRowValidResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("first_name")).thenReturn(FIRST_NAME);
        when(resultSet.getString("last_name")).thenReturn(LAST_NAME);
        when(resultSet.getString("email")).thenReturn(EMAIL);

        DentistRowMapper rowMapper = new DentistRowMapper();
        DentistEntity entity = rowMapper.mapRow(resultSet, 0);
        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(FIRST_NAME, entity.getFirstName());
        assertEquals(LAST_NAME, entity.getLastName());
        assertEquals(EMAIL, entity.getEmail());
    }

    @Test
    void mapRowResultSetThrowsException() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenThrow(new SQLException("Database error"));
        DentistRowMapper rowMapper = new DentistRowMapper();
        assertThrows(SQLException.class, () -> rowMapper.mapRow(resultSet, 0));
    }
}