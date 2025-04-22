package ru.itis.vhsroni.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dentist")
public class DentistEntity {

    @Id
    @Column(name = "dentist_id", columnDefinition = "UUID")
    @UuidGenerator
    private UUID dentistId;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "specialization", nullable = false)
    private DentistSpecialization specialization;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "education")
    private String education;

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;
}
