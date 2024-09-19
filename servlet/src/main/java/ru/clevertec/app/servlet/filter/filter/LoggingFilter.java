package ru.clevertec.app.servlet.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class LoggingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("LoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("LoggingFilter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod();
        String requestURI = httpRequest.getRequestURI();
        String clientIP = request.getRemoteAddr();
        logger.info("Request: Method = " + method + ", URI = " + requestURI + ", Client IP = " + clientIP);

        chain.doFilter(request, response);

        int status = httpResponse.getStatus();
        logger.info("Response: Method = " + method + ", URI = " + requestURI + ", Status = " + status);
    }

    @Override
    public void destroy() {
        logger.info("LoggingFilter destroyed");
    }
}