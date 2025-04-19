package ru.itis.vhsroni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HolidayListResponse {

    private String message;

    private List<HolidayResponse> holidays;
}
