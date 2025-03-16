package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets.procedure;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ProcedureEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ProcedureService;

import java.io.IOException;
import java.util.List;

@WebServlet("/procedures")
public class ProcedureReadServlet extends HttpServlet {

    private ProcedureService procedureService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        procedureService = (ProcedureService) getServletContext().getAttribute("procedureService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        List<ProcedureEntity> procedures = procedureService.getServicesList(page);
        Integer proceduresCount = procedureService.getCount();

        req.setAttribute("procedures", procedures);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", (int) Math.ceil((double) proceduresCount / 7));
        req.setAttribute("pageSize", 7);
        req.getRequestDispatcher("/jsp/procedures/procedures.jsp").forward(req, resp);

    }
}
