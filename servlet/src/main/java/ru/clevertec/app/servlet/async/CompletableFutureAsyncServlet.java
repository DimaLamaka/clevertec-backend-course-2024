package ru.clevertec.app.servlet.async;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/completable-async", asyncSupported = true)
public class CompletableFutureAsyncServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CompletableFutureAsyncServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Request received in thread: " + Thread.currentThread().getName());

        AsyncContext asyncContext = request.startAsync();

        CompletableFuture.runAsync(() -> {
            try (PrintWriter writer = asyncContext.getResponse().getWriter()){
                LOGGER.info("CompletableFuture task running in thread: " + Thread.currentThread().getName());

                // Имитация долгой задачи
                Thread.sleep(3000);

                writer.write(Thread.currentThread().toString());

                LOGGER.info("Task completed in thread: " + Thread.currentThread().getName());
            } catch (Exception e) {
                LOGGER.severe("Error in CompletableFuture task: " + e.getMessage());
            } finally {
                asyncContext.complete();
            }
        });

        LOGGER.info("Servlet thread released: " + Thread.currentThread().getName());
    }
}