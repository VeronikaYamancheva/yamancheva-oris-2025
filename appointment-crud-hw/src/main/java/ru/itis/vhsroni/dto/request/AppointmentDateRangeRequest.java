package ru.itis.vhsroni.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDateRangeRequest {

    private LocalDate startDate;

    private LocalDate endDate;
}
