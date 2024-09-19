package ru.clevertec.app.servlet.session;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    private final Gson gson;

    public SessionServlet() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        try {
            User user = new User("guest", "GUEST");
            if (session != null) {
                String login = (String) session.getAttribute("login");
                String role = (String) session.getAttribute("role");
                user = new User(login, role);
            }

            resp.setContentType("application/json");
            resp.getWriter().println(gson.toJson(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if (!"application/json".equals(req.getContentType())) {
            throw new RuntimeException("Content-Type: %s not supported".formatted(req.getContentType()));
        }

        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(10);

        try {
            BufferedReader reader = req.getReader();
            String requestJson = reader.lines()
                    .collect(Collectors.joining());
            User user = gson.fromJson(requestJson, User.class);

            session.setAttribute("role", user.role);
            session.setAttribute("login", user.login);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //close readers and writers
        }
    }

    record User(String login, String role) {
    }
}
