package ru.itis.vhsroni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.vhsroni.data.entities.DentistEntity;

import java.util.Optional;
import java.util.UUID;

public interface DentistRepository extends JpaRepository<DentistEntity, UUID> {

    Optional<DentistEntity> findByUser_Email(String email);
}
