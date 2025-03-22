package mappers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.AppointmentRowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentRowMapperTest {

    private static final Long APPOINTMENT_ID = 1L;

    private static final Long CLIENT_ID = 2L;

    private static final Long DENTIST_ID = 3L;

    private static final Date DATE = Date.valueOf("2023-10-01");

    private static final Time TIME = Time.valueOf("10:00:00");

    @Test
    void mapRowValidResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(APPOINTMENT_ID);
        when(resultSet.getLong("client_id")).thenReturn(CLIENT_ID);
        when(resultSet.getLong("dentist_id")).thenReturn(DENTIST_ID);
        when(resultSet.getDate("appointment_date")).thenReturn(DATE);
        when(resultSet.getTime("appointment_time")).thenReturn(TIME);

        AppointmentRowMapper rowMapper = new AppointmentRowMapper();
        AppointmentEntity entity = rowMapper.mapRow(resultSet, 0);
        assertNotNull(entity);
        assertEquals(APPOINTMENT_ID, entity.getId());
        assertEquals(CLIENT_ID, entity.getClientId());
        assertEquals(DENTIST_ID, entity.getDentistId());
        assertEquals(DATE, entity.getDate());
        assertEquals(TIME, entity.getTime());
    }

    @Test
    void mapRowResultSetThrowsException() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenThrow(new SQLException("Database error"));
        AppointmentRowMapper rowMapper = new AppointmentRowMapper();
        assertThrows(SQLException.class, () -> rowMapper.mapRow(resultSet, 0));
    }
}