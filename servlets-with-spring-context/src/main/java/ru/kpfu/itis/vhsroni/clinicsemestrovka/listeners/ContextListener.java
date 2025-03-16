package ru.kpfu.itis.vhsroni.clinicsemestrovka.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.AppointmentDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.AppointmentProcedureDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.DentistDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.ProcedureDao;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.dao.impl.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.mappers.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.services.impl.*;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.utils.PropertyReader;

import javax.sql.DataSource;
import java.util.List;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        ApplicationContext springContext = new ClassPathXmlApplicationContext("config/context.xml");
        context.setAttribute("springContext", springContext);

        List<String> PROTECTED_URIS = List.of(context.getContextPath() + "/profile", context.getContextPath() + "/create_appointment");;
        context.setAttribute("PROTECTED_URIS", PROTECTED_URIS);
        List<String> NOT_AUTH_URIS = List.of(context.getContextPath() + "/sign_in", context.getContextPath() + "/sign_up");
        context.setAttribute("NOT_AUTH_URIS", NOT_AUTH_URIS);

        String PROTECTED_REDIRECT = context.getContextPath() + "/unauthorized";
        context.setAttribute("PROTECTED_REDIRECT", PROTECTED_REDIRECT);
        String NOT_AUTH_REDIRECT = context.getContextPath() + "/sign_out";
        context.setAttribute("NOT_AUTH_REDIRECT", NOT_AUTH_REDIRECT);
    }
}
