package ru.itis.vhsroni.dto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

    private String message;

    private int errorCode;
}
