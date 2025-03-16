package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets.dentist;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.DentistService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/dentists/delete")
public class DentistsDeleteServlet extends HttpServlet {

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
        req.getRequestDispatcher("/jsp/dentists/dentists_delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("dentistId");
        if (id == null || id.isBlank()) {
            resp.sendRedirect(getServletContext().getContextPath() + "/dentists/delete?errors=%s".formatted(URLEncoder.encode("empty fields", StandardCharsets.UTF_8)));
        }
        try {
            dentistService.deleteById(Long.parseLong(id));
            resp.sendRedirect(getServletContext().getContextPath() + "/dentists");
        } catch (DbException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
