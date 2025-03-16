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

@WebServlet("/procedures/update")
public class ProcedureUpdateServlet extends HttpServlet {

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
        req.getRequestDispatcher("/jsp/procedures/procedures_update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("procedureId");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (name == null || description == null || id == null || id.isBlank() || name.isBlank() || description.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.sendRedirect(getServletContext().getContextPath() + "/procedures/update?errors=%s".formatted(URLEncoder.encode("you have empty fields", StandardCharsets.UTF_8)));
        }

        try {
            Optional<ProcedureEntity> updatedProcedure = procedureService.update(Long.parseLong(id), name, description);
            if (updatedProcedure.isEmpty()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/procedures/update?errors=%s".formatted(URLEncoder.encode("can't update procedure"), StandardCharsets.UTF_8));
            }
            else resp.sendRedirect(getServletContext().getContextPath() + "/procedures");
        } catch (DbException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
