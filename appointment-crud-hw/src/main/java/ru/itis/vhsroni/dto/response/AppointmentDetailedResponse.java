package ru.itis.vhsroni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailedResponse {

    UUID appointmentId;

    LocalDate date;

    LocalTime time;

    String clientFirstName;

    String clientLastName;

    String clientPatronymic;

    String clientEmail;

    String clientPhone;

    String dentistFirstName;

    String dentistLastName;

    String dentistPatronymic;

    String dentistEmail;

    String dentistPhone;
}
