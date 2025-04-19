package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.entities.ClientEntity;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.AppointmentService;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ClientService;

import java.io.IOException;
import java.util.List;

@WebServlet("/statistics")
public class StatisticsAdminServlet extends HttpServlet {

    private ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext springContext = (ApplicationContext) getServletContext().getAttribute("springContext");
        clientService = springContext.getBean("clientService", ClientService.class);    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ClientEntity> clientsList = clientService.getAllNotAdmins();
        req.setAttribute("clientsList", clientsList);
        req.getRequestDispatcher("/jsp/statistics.jsp").forward(req, resp);
    }
}
