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
@Table(name = "specialization")
public class DentistSpecialization {

    @Id
    @Column(name = "specialization_id", columnDefinition = "UUID")
    private UUID specializationId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "specialization_procedure",
            joinColumns = @JoinColumn(name = "specialization_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<ProcedureEntity> procedures;
}
