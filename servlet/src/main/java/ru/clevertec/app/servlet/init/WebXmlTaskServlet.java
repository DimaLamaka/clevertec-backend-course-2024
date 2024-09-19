package ru.clevertec.app.servlet.init;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class WebXmlTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter writer = resp.getWriter();
            getServletConfig().getInitParameterNames().asIterator().forEachRemaining(name -> {
                        String initParameter = getServletConfig().getInitParameter(name);
                        writer.println("Parameter '%s' = '%s'".formatted(name, initParameter));
                    }
            );
            resp.getWriter().println("hello world from web xml servlet tasks!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
