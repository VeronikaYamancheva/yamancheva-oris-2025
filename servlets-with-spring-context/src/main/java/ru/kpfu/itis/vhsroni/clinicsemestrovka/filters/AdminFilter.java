package ru.kpfu.itis.vhsroni.clinicsemestrovka.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/statistics", "/dentists/create", "/dentists/delete", "/dentists/update",
"/procedures/create", "/procedures/delete", "/procedures/update"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        if (session != null && ((HttpSession) session).getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(req.getServletContext().getContextPath());
        }
    }
}
