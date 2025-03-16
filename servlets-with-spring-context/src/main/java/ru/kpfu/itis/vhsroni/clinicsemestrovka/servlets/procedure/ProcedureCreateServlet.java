package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets.procedure;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ProcedureService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/procedures/create")
public class ProcedureCreateServlet extends HttpServlet {

    private ProcedureService procedureService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        procedureService = (ProcedureService) getServletContext().getAttribute("procedureService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errors = req.getParameter("errors");
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("/jsp/procedures/procedures_create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (name == null || description == null || name.isBlank() || description.isBlank()) {
            resp.sendRedirect(getServletContext().getContextPath() + "/procedures/create?errors=%s".formatted(URLEncoder.encode("empty fields", StandardCharsets.UTF_8)));
        }
        try {
            Optional<ProcedureEntity> savedProcedure = procedureService.save(name, description);
            if (savedProcedure.isEmpty()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/procedures/create".formatted(URLEncoder.encode("procedure wasn't saved", StandardCharsets.UTF_8)));
            }
            else resp.sendRedirect(getServletContext().getContextPath() + "/procedures");
        } catch (DbException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }

}
