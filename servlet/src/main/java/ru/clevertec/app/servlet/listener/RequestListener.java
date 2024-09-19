package ru.clevertec.app.servlet.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

@WebListener
public class RequestListener implements ServletRequestListener {
    private static final Logger LOGGER = Logger.getLogger(RequestListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
        LOGGER.info("Request initialized: " + httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
        LOGGER.info("Request destroyed: " + httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI());
    }
}