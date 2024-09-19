package ru.clevertec.app.servlet.init;

import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "Annotation tasks",
        urlPatterns = {
                "/annotation-tasks",
                "/annotation"
        },
        initParams = {
                @WebInitParam(name = "annotation.config.param1", value = "value1"),
                @WebInitParam(name = "annotation.config.param2", value = "value2")
        },
        loadOnStartup = 1
)
public class AnnotationTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter writer = resp.getWriter();
            getServletConfig().getInitParameterNames().asIterator().forEachRemaining(name -> {
                        String initParameter = getServletConfig().getInitParameter(name);
                        writer.println("Parameter '%s' = '%s'".formatted(name, initParameter));
                    }
            );
            resp.getWriter().println("hello world from annotation servlet tasks!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
