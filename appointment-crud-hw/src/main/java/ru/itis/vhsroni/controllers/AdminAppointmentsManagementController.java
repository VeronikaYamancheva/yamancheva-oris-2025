package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.dto.request.AppointmentCreationRequest;
import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.dto.response.AppointmentResponse;
import ru.itis.vhsroni.services.AdminAppointmentManagementService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/appointments")
@RequiredArgsConstructor
public class AdminAppointmentsManagementController {

    private final AdminAppointmentManagementService adminAppointmentManagementService;

    @GetMapping
    public List<AppointmentResponse> getAllAppointments() {
        return adminAppointmentManagementService.findAllAppointments();
    }

    @GetMapping("/by_client_email")
    public List<AppointmentResponse> getAppointmentsByClientEmail(@RequestParam("email") String email) {
        return adminAppointmentManagementService.findAppointmentByClientEmail(email);
    }

    @GetMapping("/{appointmentId}")
    public AppointmentDetailedResponse getAppointmentDetailedInfoById(@PathVariable("appointmentId") UUID appointmentId) {
        return adminAppointmentManagementService.findDetailedInfoByAppointmentId(appointmentId);
    }

    @GetMapping("/date_range")
    public List<AppointmentDetailedResponse> getAppointmentsByDateRange(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return adminAppointmentManagementService.findAppointmentsDetailedInfoByDateRange(startDate, endDate);
    }

    @PostMapping("/new")
    public UUID createNewAppointment(@RequestBody AppointmentCreationRequest request) {
        return adminAppointmentManagementService.createNewAppointmentByClientAndDentistsEmails(request.getClientEmail(), request.getDentistEmail(), request.getDate(), request.getTime());
    }

    @PatchMapping("/{appointmentId}")
    public String updateAppointmentDateTime(
            LocalDate date,
            LocalTime time,
            @PathVariable("appointmentId") UUID appointmentId
    ) {
        boolean result = adminAppointmentManagementService.updateAppointmentDateTime(date, time, appointmentId);
        if (result) {
            return "Successful update";
        } else {
            return "Unsuccessful update";
        }
    }

    @DeleteMapping("/{appointmentId}")
    public String deleteAppointmentById(@PathVariable("appointmentId") UUID appointmentId) {
        adminAppointmentManagementService.deleteAppointmentById(appointmentId);
        return "Appointment was deleted";
    }
}
