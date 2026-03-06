package com.example.demoVanillaJava.api.rest.servlets;

import com.example.demoVanillaJava.shared.dto.CounterDto;
import com.example.demoVanillaJava.shared.dto.CreateUserDto;
import com.example.demoVanillaJava.spi.prometheus.PrometheusService;
import com.example.demoVanillaJava.spi.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PrometheusServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PrometheusService prometheusService = PrometheusService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        CounterDto counterDto = objectMapper.readerFor(CounterDto.class).readValue(body);

        Counter.builder("app.requests.total")
                .description("Total number of requests")
                .tag("service", "micrometer-demo")
                .register(prometheusService.getMeterRegistry());

        prometheusService.getMeterRegistry()
                .newCounter(new Meter.Id(
                        counterDto.getName(),
                        Tags.of(counterDto.getTags().stream().map(t -> Tag.of(t.getName(), t.getValue())).toList()),
                        null,
                        null,
                        Meter.Type.COUNTER));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        CounterDto counterDto = objectMapper.readerFor(CounterDto.class).readValue(body);

        prometheusService.getMeterRegistry().counter(counterDto.getName(),
                Tags.of(counterDto.getTags().stream().map(t -> Tag.of(t.getName(), t.getValue())).toList()))
                .increment();
    }

}
