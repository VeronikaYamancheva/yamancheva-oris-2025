package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.data.entities.AppointmentEntity;
import ru.itis.vhsroni.data.entities.ClientEntity;
import ru.itis.vhsroni.data.entities.DentistEntity;
import ru.itis.vhsroni.data.mappers.AppointmentMapper;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.dto.response.AppointmentResponse;
import ru.itis.vhsroni.exceptions.AppointmentNotFoundException;
import ru.itis.vhsroni.exceptions.ClientNotFoundException;
import ru.itis.vhsroni.exceptions.DentistNotFoundException;
import ru.itis.vhsroni.repositories.AppointmentRepository;
import ru.itis.vhsroni.repositories.AppointmentRepositoryCustom;
import ru.itis.vhsroni.repositories.ClientRepository;
import ru.itis.vhsroni.repositories.DentistRepository;
import ru.itis.vhsroni.services.AdminAppointmentManagementService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAppointmentManagementServiceImpl implements AdminAppointmentManagementService {

    private final AppointmentRepositoryCustom appointmentRepositoryCustom;

    private final AppointmentRepository appointmentRepository;

    private final ClientRepository clientRepository;

    private final DentistRepository dentistRepository;

    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentResponse> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public UUID createNewAppointmentByClientAndDentistsEmails(String clientEmail, String dentistEmail, LocalDate date, LocalTime time) {
        Optional<ClientEntity> clientOptional = clientRepository.findByUser_Email(clientEmail);
        Optional<DentistEntity> dentistOptional = dentistRepository.findByUser_Email(dentistEmail);
        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException();
        }
        if (dentistOptional.isEmpty()) {
            throw new DentistNotFoundException();
        }
        AppointmentEntity newAppointment = appointmentRepository.save(AppointmentEntity.builder()
                .client(clientOptional.get())
                .dentist(dentistOptional.get())
                .date(date)
                .time(time)
                .build());

        return newAppointment.getAppointmentId();
    }

    @Override
    public AppointmentDetailedResponse findDetailedInfoByAppointmentId(UUID appointmentId) {
        Optional<AppointmentEntity> appointmentOptional = appointmentRepository.findDetailedInfoById(appointmentId);
        if (appointmentOptional.isEmpty()) {
            throw new AppointmentNotFoundException();
        }
        return appointmentMapper.toDetailedResponse(appointmentOptional.get());
    }

    @Override
    public List<AppointmentResponse> findAppointmentByClientEmail(String email) {
        return appointmentRepository.findByClient_User_Email(email).stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentDetailedResponse> findAppointmentsDetailedInfoByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepositoryCustom.findAppointmentDetailedInfoByDateRange(startDate, endDate);
    }

    @Override
    public boolean updateAppointmentDateTime(LocalDate date, LocalTime time, UUID appointmentId) {
        Optional<AppointmentEntity> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isEmpty()) {
            throw new AppointmentNotFoundException();
        }
        int result = appointmentRepository.updateAppointmentDateTime(appointmentId, date, time);
        return result == 1;
    }

    @Override
    public void deleteAppointmentById(UUID appointmentId) {
        appointmentRepository.deleteByAppointmentId(appointmentId);
    }
}
