package ru.clevertec.app.servlet.lifecycle;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "LifecycleServlet",
        urlPatterns = {"/lifecycle"}
)
public class LifecycleServlet extends HttpServlet {

    public LifecycleServlet() {
        System.out.println("Constructor called, thread: " + Thread.currentThread());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("init() method called, thread: " + Thread.currentThread());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doGet() method called, thread:" + Thread.currentThread());
        PrintWriter out = response.getWriter();
        out.println("hello from lifecycle servlet, thread:" + Thread.currentThread());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("doPost() method called, thread:" + Thread.currentThread());
        doGet(request, response);  // Чтобы не дублировать код, вызываем doGet()
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service() method called, thread:" + Thread.currentThread());
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("destroy() method called, thread:" + Thread.currentThread());
    }
}
