package ru.vhsroni.api.dto.response;

import lombok.*;

/**
 * Типовой ответ для операций, где может быть более двух возможных сценариев.
 * operationStatus - числовой код сценария;
 * description - текстовое описание сценария для отображения пользователю
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationResponse {

    private int operationStatus;

    private String description;

}
