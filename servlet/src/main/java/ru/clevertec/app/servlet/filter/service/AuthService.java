package ru.clevertec.app.servlet.filter.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.clevertec.app.servlet.filter.model.TokenResponse;

import static java.util.Objects.*;

public class AuthService {
    public static final String ROLE_ATTRIBUTE = "ROLE";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";

    private final UserService userService;
    private final TokenService tokenService;

    public AuthService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public TokenResponse authenticate(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isNull(username) || isNull(password)) {
            throw new IllegalArgumentException("Username and password are required");
        }

        return userService.findByUsername(username)
                .filter(user -> this.auth(password, user.password()))
                .map(tokenService::generateTokenByUser)
                .map(TokenResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }

    public boolean isAdmin(HttpServletRequest request) {
        String role = (String) request.getAttribute(ROLE_ATTRIBUTE);
        return ADMIN_ROLE.equalsIgnoreCase(role);
    }

    public boolean isUser(HttpServletRequest request) {
        String role = (String) request.getAttribute(ROLE_ATTRIBUTE);
        return USER_ROLE.equalsIgnoreCase(role);
    }

    private boolean auth(String password, String userPassword) {
        return password.equals(userPassword);
    }
}
