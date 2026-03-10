package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.service.AuthorizationService;
import com.example.demoVanillaJava.shared.dto.LoginDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthorizationService authorizationService = AuthorizationService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        LoginDto loginDto = objectMapper.readerFor(LoginDto.class).readValue(body);
        String jwt = authorizationService.authentication(loginDto);
        if (jwt == null) {
            resp.setStatus(401);
            return;
        }
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(objectMapper.writeValueAsString(jwt));
    }
}
