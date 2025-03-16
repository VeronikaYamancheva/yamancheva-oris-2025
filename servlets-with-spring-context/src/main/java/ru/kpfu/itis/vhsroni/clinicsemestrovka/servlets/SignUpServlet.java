package ru.kpfu.itis.vhsroni.clinicsemestrovka.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dto.ClientSignResponseDto;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DbException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.SignException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.ClientService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/sign_up")
public class SignUpServlet extends HttpServlet {

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
        req.getRequestDispatcher("/jsp/sign/sign_up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ClientSignResponseDto result = clientService.save(
                    req.getParameter("email"),
                    req.getParameter("nickname"),
                    req.getParameter("password"),
                    req.getParameter("cpassword"),
                    req.getParameter("adminCode")
            );

            if (result.getClient().isPresent()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/sign_in");
            } else if (!result.getErrors().isEmpty()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/sign_up?errors=%s".formatted(result.getErrors(), StandardCharsets.UTF_8));
            } else {
                throw new SignException("You have valid parameters, but you didn't saved.");
            }
        } catch (DbException | SignException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.sendRedirect(getServletContext().getContextPath() + "/error");
        }
    }
}
