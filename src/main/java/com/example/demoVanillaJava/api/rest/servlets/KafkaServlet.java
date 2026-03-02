package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.api.kafka.KafkaService;
import com.example.demoVanillaJava.service.UserService;
import com.example.demoVanillaJava.shared.dto.CreateUserDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class KafkaServlet extends HttpServlet {

    private final KafkaService kafkaService = new KafkaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        kafkaService.sendMessage(new String(req.getInputStream().readAllBytes()));
    }

}
