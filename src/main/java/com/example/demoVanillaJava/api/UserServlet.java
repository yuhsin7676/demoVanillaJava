package com.example.demoVanillaJava.api;

import com.example.demoVanillaJava.service.UserService;
import com.example.demoVanillaJava.shared.dto.CreateUserDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.example.demoVanillaJava.spi.SelectDBConnector;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.getWriter().print(objectMapper.writeValueAsString(userService.getAll()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        CreateUserDto createUserDto = objectMapper.readerFor(CreateUserDto.class).readValue(body);
        userService.create(createUserDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        UserDto updateUserDto = objectMapper.readerFor(UserDto.class).readValue(body);
        userService.update(updateUserDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.parseLong(req.getParameter("id"));
        userService.delete(id);
    }

}
