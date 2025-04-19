package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.DentistEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.DentistService;

import java.io.IOException;
import java.util.List;

@WebServlet("/dentists")
public class DentistsReadServlet extends HttpServlet {

    private DentistService dentistService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext springContext = (ApplicationContext) getServletContext().getAttribute("springContext");
        dentistService = springContext.getBean(DentistService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;

        List<DentistEntity> dentists = dentistService.getServicesList(page);
        Integer dentistCount = dentistService.getCount();

        req.setAttribute("dentists", dentists);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", (int) Math.ceil((double) dentistCount / 7));
        req.setAttribute("pageSize", 7);
        req.getRequestDispatcher("/jsp/dentists/dentists.jsp").forward(req, resp);
    }
}
