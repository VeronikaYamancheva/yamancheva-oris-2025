package ru.itis.vhsroni.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "procedure")
public class ProcedureEntity {

    @Id
    @Column(name = "procedure_id", columnDefinition = "UUID")
    private UUID procedureId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "procedures")
    private Set<DentistSpecialization> specializations;
}