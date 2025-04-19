package ru.kpfu.itis.vhsroni.clinicsemestrovka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientSignResponseDto {

    private Optional<ClientEntity> client;

    private String errors;

    private Map<String, String> validInputs;
}
