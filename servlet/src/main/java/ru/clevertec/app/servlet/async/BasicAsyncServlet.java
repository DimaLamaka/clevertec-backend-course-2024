package ru.clevertec.app.servlet.async;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/basic-async", asyncSupported = true)
public class BasicAsyncServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(BasicAsyncServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Request received in thread: " + Thread.currentThread().getName());

        AsyncContext asyncContext = request.startAsync();

        asyncContext.start(() -> {
            try (PrintWriter writer = asyncContext.getResponse().getWriter()){
                LOGGER.info("Async task running in thread: " + Thread.currentThread().getName());

                Thread.sleep(3000);

                writer.write(Thread.currentThread().toString());

                LOGGER.info("Task completed in thread: " + Thread.currentThread().getName());
            } catch (Exception e) {
                LOGGER.severe("Error in async task: " + e.getMessage());
            } finally {
                asyncContext.complete();
            }
        });

        LOGGER.info("Servlet thread released: " + Thread.currentThread().getName());
    }
}