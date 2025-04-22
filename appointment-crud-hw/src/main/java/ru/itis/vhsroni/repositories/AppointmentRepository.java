package ru.itis.vhsroni.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vhsroni.data.entities.AppointmentEntity;
import ru.itis.vhsroni.data.entities.ClientEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {

    List<AppointmentEntity> findByClient_User_Email(String email);

    @Modifying
    @Transactional
    void deleteByAppointmentId(UUID appointmentId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE appointment SET time = :newTime, date = :newDate WHERE appointment_id = :appointmentId", nativeQuery = true)
    int updateAppointmentDateTime(
            @Param("appointmentId") UUID appointmentId,
            @Param("newDate") LocalDate newDate,
            @Param("newTime") LocalTime newTime
    );

    @Query("""
                SELECT a FROM AppointmentEntity a
                JOIN a.client c
                JOIN c.user cu
                JOIN a.dentist d
                JOIN d.user du
                WHERE a.appointmentId = :appointmentId
            """)
    Optional<AppointmentEntity> findDetailedInfoById(
            @Param("appointmentId") UUID appointmentId
    );


}
