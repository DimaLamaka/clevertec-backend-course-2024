package ru.clevertec.app.servlet.filter.servlet;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.app.servlet.filter.model.TokenResponse;
import ru.clevertec.app.servlet.filter.repository.UserRepository;
import ru.clevertec.app.servlet.filter.service.AuthService;
import ru.clevertec.app.servlet.filter.service.TokenService;
import ru.clevertec.app.servlet.filter.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    private final AuthService authService;
    private final Gson gson;

    public LoginServlet() {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        TokenService tokenService = new TokenService();
        authService = new AuthService(userService, tokenService);
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("LoginServlet POST request received");
        try (PrintWriter printWriter = response.getWriter()) {
            TokenResponse tokenResponse = authService.authenticate(request);
            String jsonResponse = gson.toJson(tokenResponse);

            response.setContentType("application/json");
            printWriter.write(jsonResponse);
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}