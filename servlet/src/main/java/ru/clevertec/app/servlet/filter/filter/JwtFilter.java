package ru.clevertec.app.servlet.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.app.servlet.filter.service.AuthService;
import ru.clevertec.app.servlet.filter.service.TokenService;

import java.io.IOException;
import java.util.logging.Logger;

public class JwtFilter implements Filter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Logger logger = Logger.getLogger(JwtFilter.class.getName());

    private final TokenService tokenService;

    public JwtFilter() {
        this.tokenService = new TokenService();
    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("JwtFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        logger.info("JwtFilter doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader(AUTHORIZATION_HEADER);

        try {
            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                String token = authHeader.substring(7);

                tokenService.getRoleFromToken(token)
                        .ifPresent(role -> httpRequest.setAttribute(AuthService.ROLE_ATTRIBUTE, role));

                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            }
        } catch (Exception e) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        }
    }

    @Override
    public void destroy() {
        logger.info("JwtFilter destroyed");
    }
}