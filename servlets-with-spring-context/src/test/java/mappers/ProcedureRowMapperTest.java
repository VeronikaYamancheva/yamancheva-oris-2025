package mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.ProcedureRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcedureRowMapperTest {

    private static final Long ID = 1L;

    private static final String NAME = "name";

    private static final String DESCRIPTION = "description";

    @Test
    void mapRowValidResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getString("description")).thenReturn(DESCRIPTION);

        ProcedureRowMapper rowMapper = new ProcedureRowMapper();
        ProcedureEntity entity = rowMapper.mapRow(resultSet, 0);
        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(NAME, entity.getName());
        assertEquals(DESCRIPTION, entity.getDescription());
    }

    @Test
    void mapRowResultSetThrowsException() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenThrow(new SQLException("Database error"));
        ProcedureRowMapper rowMapper = new ProcedureRowMapper();
        assertThrows(SQLException.class, () -> rowMapper.mapRow(resultSet, 0));
    }
}