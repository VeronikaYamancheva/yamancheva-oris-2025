package ru.kpfu.itis.vhsroni.clinicsemestrovka.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureEntity {

    Long id;

    String name;

    String description;

    public ProcedureEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
