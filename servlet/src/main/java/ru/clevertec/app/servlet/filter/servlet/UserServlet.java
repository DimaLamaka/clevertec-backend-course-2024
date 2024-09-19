package ru.clevertec.app.servlet.filter.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.app.servlet.filter.repository.UserRepository;
import ru.clevertec.app.servlet.filter.service.AuthService;
import ru.clevertec.app.servlet.filter.service.TokenService;
import ru.clevertec.app.servlet.filter.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/secure/user")
public class UserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());

    private final AuthService authService;

    public UserServlet() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        TokenService tokenService = new TokenService();
        authService = new AuthService(userService, tokenService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("UserServlet GET request received");
        try (PrintWriter printWriter = response.getWriter()) {
            if (authService.isUser(request)) {
                printWriter.write("Welcome, USER!");
            } else if (authService.isAdmin(request)) {
                printWriter.write("Welcome, ADMIN!");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                printWriter.write("Access denied");
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}