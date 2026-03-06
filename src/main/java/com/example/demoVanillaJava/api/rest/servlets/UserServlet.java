package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.service.UserService;
import com.example.demoVanillaJava.shared.dto.CreateUserDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@OpenAPIDefinition(
//        info = @Info(title = "My Non-Spring API", version = "1.0.0"),
//        servers = { @Server(url = "/user") }
//)
public class UserServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService = new UserService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(objectMapper.writeValueAsString(userService.getAll()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String body = new String(req.getInputStream().readAllBytes());
        CreateUserDto createUserDto = objectMapper.readerFor(CreateUserDto.class).readValue(body);
        userService.create(createUserDto);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        String body = new String(req.getInputStream().readAllBytes());
        UserDto updateUserDto = objectMapper.readerFor(UserDto.class).readValue(body);
        userService.update(updateUserDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setCharacterEncoding("UTF-8");
        Long id = Long.parseLong(req.getParameter("id"));
        userService.delete(id);
    }

}
