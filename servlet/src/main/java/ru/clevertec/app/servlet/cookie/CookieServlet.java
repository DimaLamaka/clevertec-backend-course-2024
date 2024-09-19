package ru.clevertec.app.servlet.cookie;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/cookie")
public class CookieServlet extends HttpServlet {
    private final Gson gson;

    public CookieServlet() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        printAllCookies(req);
        
        String locale = Optional.ofNullable(req.getParameter("locale"))
                .map(Locale::of)
                .map(Locale::toLanguageTag)
                .orElse(null);

        try {
            if (Objects.nonNull(locale)) {
                Cookie localeCookie = new Cookie("locale", locale);
                localeCookie.setMaxAge(10);
                resp.addCookie(localeCookie);
            } else {
                locale = Optional.ofNullable(req.getCookies())
                        .stream()
                        .flatMap(Arrays::stream)
                        .filter(cookie -> cookie.getName().equals("locale"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse("EN");
            }

            Resource resource = getResourceByLocale(locale);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter writer = resp.getWriter();
            writer.print(gson.toJson(resource));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void printAllCookies(HttpServletRequest request) {
        System.out.println("Cookies:");
        Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .forEach(cookie -> System.out.println("Cookie name: %s -> value: %s".formatted(cookie.getName(), cookie.getValue())));
    }

    private Resource getResourceByLocale(String locale) {
        if ("en".equalsIgnoreCase(locale)) {
            return new Resource("Message");
        } else if ("ru".equalsIgnoreCase(locale)) {
            return new Resource("Сообщение");
        }
        throw new IllegalArgumentException("Invalid locale: " + locale);
    }

    record Resource(String message) {
    }
}
