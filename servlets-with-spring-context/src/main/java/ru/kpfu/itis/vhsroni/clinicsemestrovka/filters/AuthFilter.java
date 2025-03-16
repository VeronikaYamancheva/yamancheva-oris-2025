package ru.kpfu.itis.vhsroni.clinicsemestrovka.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private List<String> PROTECTED_URIS;
    private List<String> NOT_AUTH_URIS;
    private String PROTECTED_REDIRECT;
    private String NOT_AUTH_REDIRECT;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ServletContext context = filterConfig.getServletContext();
        PROTECTED_URIS = (List<String>) context.getAttribute("PROTECTED_URIS");
        NOT_AUTH_URIS = (List<String>) context.getAttribute("NOT_AUTH_URIS");

        PROTECTED_REDIRECT = (String) context.getAttribute("PROTECTED_REDIRECT");
        NOT_AUTH_REDIRECT = (String) context.getAttribute("NOT_AUTH_REDIRECT");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if (isUserAuth(request)) {
            if (isNotAuthUri(uri)) {
                response.sendRedirect(NOT_AUTH_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            if (isProtectedUri(uri)) {
                response.sendRedirect(PROTECTED_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean isProtectedUri(String uri) {
        return PROTECTED_URIS.contains(uri);
    }

    private boolean isNotAuthUri(String uri) {
        return NOT_AUTH_URIS.contains(uri);
    }

    private boolean isUserAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Boolean flag = (Boolean) session.getAttribute("authorization");
        return flag != null && flag;
    }
}