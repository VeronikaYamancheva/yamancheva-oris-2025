package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ClientService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/sign_in")
public class SignInServlet extends HttpServlet {

    private ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        clientService = (ClientService) getServletContext().getAttribute("clientService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errors = req.getParameter("errors");
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("/jsp/sign/sign_in.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ClientSignResponseDto result = clientService.authorize(
                req.getParameter("nickname"),
                req.getParameter("password")
        );
        if (result.getClient().isPresent()) {
            if (result.getClient().get().isAdmin()) req.getSession().setAttribute("isAdmin", true);
            req.getSession().setAttribute("authorization", true);
            req.getSession().setAttribute("client", result.getClient().get());
            resp.sendRedirect(getServletContext().getContextPath());
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/sign_in?errors=%s".formatted(URLEncoder.encode(result.getErrors(), StandardCharsets.UTF_8)));
        }
    }
}
