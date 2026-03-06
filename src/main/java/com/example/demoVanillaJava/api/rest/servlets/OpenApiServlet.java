package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.service.MyOpenApiGenerator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class OpenApiServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        resp.getWriter().print(objectMapper.writeValueAsString(MyOpenApiGenerator.createOpenAPI()));
    }
}