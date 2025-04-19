package ru.kpfu.itis.vhsroni.clinicsemestrovka.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentProcedureEntity {

    Long id;

    Long appointmentId;

    Long procedureId;

    public AppointmentProcedureEntity(Long appointmentId, Long procedureId) {
        this.appointmentId = appointmentId;
        this.procedureId = procedureId;
    }
}
