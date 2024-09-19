package ru.clevertec.app.servlet.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Application started: " + sce.getServletContext().getContextPath());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Application stopped.");
    }
}
