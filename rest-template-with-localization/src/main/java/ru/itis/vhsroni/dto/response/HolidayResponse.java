package ru.itis.vhsroni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HolidayResponse {

    private String date;

    private String name;

    private String description;
}
