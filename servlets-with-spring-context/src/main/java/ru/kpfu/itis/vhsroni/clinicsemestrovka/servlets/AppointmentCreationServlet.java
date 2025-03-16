package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DateTimeValidationException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.AppointmentService;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.AppointmentDateTimeValidator;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/create_appointment")
public class AppointmentCreationServlet extends HttpServlet {

    private AppointmentService appointmentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appointmentService = (AppointmentService) getServletContext().getAttribute("appointmentService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DentistEntity> dentists = appointmentService.getAllDentists();
        List<ProcedureEntity> procedures = appointmentService.getAllProcedures();

        req.setAttribute("dentists", dentists);
        req.setAttribute("procedures", procedures);

        String errors = req.getParameter("errors");
        req.setAttribute("errors", errors);

        req.getRequestDispatcher("/jsp/appointment_create.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        Long dentistId = Long.parseLong(req.getParameter("dentistId"));
        LocalDate localDate = LocalDate.parse(req.getParameter("appointmentDate"));
        LocalTime localTime = LocalTime.parse(req.getParameter("appointmentTime"));
        String[] proceduresParam = req.getParameterValues("procedures");

        try {
            AppointmentDateTimeValidator.checkValidDateAndTime(localDate, localTime);
        } catch (DateTimeValidationException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/create_appointment?errors=%s".formatted(URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8)));
            return;
        }
        Date date = Date.valueOf(localDate);
        Time time = Time.valueOf(localTime);
        Long[] proceduresId = new Long[proceduresParam.length];
        for (int i = 0; i < proceduresParam.length; i++)
            proceduresId[i] = Long.parseLong(proceduresParam[i]);
        try {
            Optional<AppointmentEntity> appointment = appointmentService.bookAppointment(firstName, lastName, email, dentistId, date, time, proceduresId);
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(getServletContext().getContextPath() + "/create_appointment?errors=%s".formatted(URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8)));
        } catch (DbException e) {;
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
