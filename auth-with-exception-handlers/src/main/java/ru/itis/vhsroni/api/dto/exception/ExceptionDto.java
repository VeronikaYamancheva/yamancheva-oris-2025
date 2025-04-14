package ru.itis.vhsroni.api.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    private int errorCode;

    private String errorMessage;

    private String errorTimestamp;

    private String errorUrl;
}
