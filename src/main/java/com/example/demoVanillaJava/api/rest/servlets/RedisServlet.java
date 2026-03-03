package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.spi.redis.RedisService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RedisServlet extends HttpServlet {

    private final RedisService redisService = new RedisService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String val = redisService.get(req.getParameter("key"));
        resp.getWriter().print(val);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        redisService.set(req.getParameter("key"), req.getParameter("value"));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        redisService.inkr(req.getParameter("key"));
    }

}
