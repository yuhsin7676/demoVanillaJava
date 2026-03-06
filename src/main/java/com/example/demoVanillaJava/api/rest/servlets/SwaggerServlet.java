package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.api.rest.swagger.SwaggerService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;


public class SwaggerServlet extends HttpServlet {

    private final SwaggerService swaggerService = new SwaggerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        SwaggerService.Resource resource = swaggerService.getView(req.getPathInfo());
        resp.setCharacterEncoding("UTF-8");

        // Устанавливаем правильный Content-Type
        if (resource.getResourcePath().endsWith(".html")) {
            resp.setContentType("text/html; charset=UTF-8");
        } else if (resource.getResourcePath().endsWith(".css")) {
            resp.setContentType("text/css");
        } else if (resource.getResourcePath().endsWith(".js")) {
            resp.setContentType("application/javascript");
        } else if (resource.getResourcePath().endsWith(".png")) {
            resp.setContentType("image/png");
        } else if (resource.getResourcePath().endsWith(".svg")) {
            resp.setContentType("image/svg+xml");
        } else if (resource.getResourcePath().endsWith(".ico")) {
            resp.setContentType("image/x-icon");
        } else if (resource.getResourcePath().endsWith(".json")) {
            resp.setContentType("application/json");
        }

        // Копируем ресурс в выходной поток
        try (OutputStream out = resp.getOutputStream()) {
            IOUtils.copy(resource.getResourceStream(), out);
        } finally {
            resource.getResourceStream().close();
        }

    }
}