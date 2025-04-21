package ru.itis.vhsroni.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.itis.vhsroni.data.entities.AppointmentEntity;
import ru.itis.vhsroni.data.entities.ClientEntity;
import ru.itis.vhsroni.data.entities.DentistEntity;
import ru.itis.vhsroni.data.entities.UserEntity;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.repositories.AppointmentRepositoryCustom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentRepositoryCustomImpl implements AppointmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AppointmentDetailedResponse> findAppointmentDetailedInfoByDateRange(LocalDate startDate, LocalDate endDate) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppointmentDetailedResponse> query = cb.createQuery(AppointmentDetailedResponse.class);
        Root<AppointmentEntity> appointment = query.from(AppointmentEntity.class);
        Join<AppointmentEntity, ClientEntity> client = appointment.join("client");
        Join<ClientEntity, UserEntity> clientUser = client.join("user");
        Join<AppointmentEntity, DentistEntity> dentist = appointment.join("dentist");
        Join<DentistEntity, UserEntity> dentistUser = dentist.join("user");
        List<Predicate> predicates = new ArrayList<>();
        if (startDate != null && endDate != null) {
            predicates.add(cb.between(appointment.get("date"), startDate, endDate));
        } else if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(appointment.get("date"), startDate));
        } else if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(appointment.get("date"), endDate));
        }
        query.select(cb.construct(
                AppointmentDetailedResponse.class,
                appointment.get("appointmentId"),
                appointment.get("date"),
                appointment.get("time"),
                clientUser.get("firstName"),
                clientUser.get("lastName"),
                clientUser.get("patronymic"),
                clientUser.get("email"),
                clientUser.get("phoneNumber"),
                dentistUser.get("firstName"),
                dentistUser.get("lastName"),
                dentistUser.get("patronymic"),
                dentistUser.get("email"),
                dentistUser.get("phoneNumber")
        ));
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }


}
