package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.spi.prometheus.PrometheusService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MetricsServlet extends HttpServlet {

    private final PrometheusService prometheusService = PrometheusService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String response = prometheusService.getMeterRegistry().scrape();
        resp.setHeader("Content-Type", "text/plain; version=0.0.4; charset=utf-8");
        resp.getWriter().print(response);
    }

}
