package ru.itis.vhsroni.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CalendarificApiResponse {

    private Meta meta;

    private Response response;

    @Data
    public static class Meta {

        private int code;
    }

    @Data
    public static class Response {

        private List<Holiday> holidays;
    }

    @Data
    public static class Holiday {

        private String name;

        private String description;

        private DateObj date;
    }

    @Data
    public static class DateObj {

        private String iso;
    }
}