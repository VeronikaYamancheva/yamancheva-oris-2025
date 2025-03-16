package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets.procedure;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ProcedureService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/procedures/delete")
public class ProcedureDeleteServlet extends HttpServlet {

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
        req.getRequestDispatcher("/jsp/procedures/procedures_delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("procedureId");
        System.out.println(id);
        if (id == null || id.isBlank()) {
            resp.sendRedirect(getServletContext().getContextPath() + "/procedures/delete?errors=%s".formatted(URLEncoder.encode("empty fields", StandardCharsets.UTF_8)));
            return;
        }
        try {
            procedureService.deleteById(Long.parseLong(id));
            resp.sendRedirect(getServletContext().getContextPath() + "/procedures");
        } catch (DbException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
