package mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentProcedureRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentProcedureRowMapperTest {

    private static final Long APPOINTMENT_ID = 1L;

    private static final Long PROCEDURE_ID = 2L;

    @Test
    void mapRowValidResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("appointment_id")).thenReturn(APPOINTMENT_ID);
        when(resultSet.getLong("procedure_id")).thenReturn(PROCEDURE_ID);

        AppointmentProcedureRowMapper rowMapper = new AppointmentProcedureRowMapper();
        AppointmentProcedureEntity entity = rowMapper.mapRow(resultSet, 0);
        assertNotNull(entity);
        assertEquals(APPOINTMENT_ID, entity.getAppointmentId());
        assertEquals(PROCEDURE_ID, entity.getProcedureId());
    }

    @Test
    void mapRowResultSetThrowsException() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("appointment_id")).thenThrow(new SQLException("Database error"));
        AppointmentProcedureRowMapper rowMapper = new AppointmentProcedureRowMapper();
        assertThrows(SQLException.class, () -> rowMapper.mapRow(resultSet, 0));
    }
}