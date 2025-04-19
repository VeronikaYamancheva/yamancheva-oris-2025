package ru.kpfu.itis.vhsroni.clinicsemestrovka.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEntity {

    private Long id;

    private Long clientId;

    private Long dentistId;

    private Date date;

    private Time time;

    public AppointmentEntity(Long clientId, Long dentistId, Date date, Time time) {
        this.clientId = clientId;
        this.dentistId = dentistId;
        this.date = date;
        this.time = time;
    }
}
