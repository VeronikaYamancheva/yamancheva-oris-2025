package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.AppointmentEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.AppointmentService;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ClientService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {


    private ClientService clientService;
    private AppointmentService appointmentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        clientService = (ClientService) getServletContext().getAttribute("clientService");
        appointmentService = (AppointmentService) getServletContext().getAttribute("appointmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientEntity client = (ClientEntity) req.getSession().getAttribute("client");
        List<Map<AppointmentEntity, List<ProcedureEntity>>> appointments = appointmentService.getAllWithProceduresByClientId(client.getId());

        String errors = req.getParameter("errors");
        req.setAttribute("errors", errors);

        req.setAttribute("appointments", appointments);
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientEntity currentClient = (ClientEntity) req.getSession().getAttribute("client");

        try {
            ClientSignResponseDto result = clientService.update(
                    req.getParameter("firstName"),
                    req.getParameter("lastName"),
                    currentClient.getEmail()
            );

            if (result.getErrors().isEmpty() && result.getClient().isPresent()) {
                req.getSession().setAttribute("client", result.getClient().get());
            }
            else {
                resp.sendRedirect(getServletContext().getContextPath() + "/profile?errors=%s".formatted(URLEncoder.encode(result.getErrors().toString(), StandardCharsets.UTF_8)));
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
        } catch (DbException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
