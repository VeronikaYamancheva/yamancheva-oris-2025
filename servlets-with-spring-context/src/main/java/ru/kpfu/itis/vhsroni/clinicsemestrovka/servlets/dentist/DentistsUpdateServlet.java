package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets.dentist;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.DentistService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/dentists/update")
public class DentistsUpdateServlet extends HttpServlet {

    private DentistService dentistService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dentistService = (DentistService) getServletContext().getAttribute("dentistService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errors = req.getParameter("errors");
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("/jsp/dentists/dentists_update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("dentistId");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        if ( firstName == null || lastName == null || email == null || id == null || id.isBlank() || firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
            resp.sendRedirect(getServletContext().getContextPath() + "/dentists/update?errors=%s".formatted(URLEncoder.encode("you have empty fields", StandardCharsets.UTF_8)));
        }

        try {
            Optional<DentistEntity> updatedDentist = dentistService.update(Long.parseLong(id), firstName, lastName, email);
            if (updatedDentist.isEmpty()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/dentist/update?errors=%s".formatted(URLEncoder.encode("can't update dentist"), StandardCharsets.UTF_8));
            }
            else resp.sendRedirect(getServletContext().getContextPath() + "/dentists");
        } catch (DbException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
