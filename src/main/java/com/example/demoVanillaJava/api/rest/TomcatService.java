package com.example.demoVanillaJava.api.rest;

import com.example.demoVanillaJava.api.rest.servlets.*;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.JarResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class TomcatService {

    public void start() {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);
            tomcat.getConnector();

            // Создание контекста
            Context ctx = tomcat.addContext("", null);

            // Добавление сервлета
            Tomcat.addServlet(ctx, "user", new UserServlet());
            ctx.addServletMappingDecoded("/user", "user");
            Tomcat.addServlet(ctx, "kafka", new KafkaServlet());
            ctx.addServletMappingDecoded("/sendIntoKafka", "kafka");
            Tomcat.addServlet(ctx, "redis", new RedisServlet());
            ctx.addServletMappingDecoded("/redis", "redis");
            Tomcat.addServlet(ctx, "swagger", new SwaggerServlet());
            ctx.addServletMappingDecoded("/swagger-ui/*", "swagger");
            Tomcat.addServlet(ctx, "openapi", new OpenApiServlet());
            ctx.addServletMappingDecoded("/openapi/*", "openapi");
            Tomcat.addServlet(ctx, "prometheus", new PrometheusServlet());
            ctx.addServletMappingDecoded("/prometheus", "prometheus");
            Tomcat.addServlet(ctx, "prometheusGet", new PrometheusGetServlet());
            ctx.addServletMappingDecoded("/prometheus/get", "prometheusGet");
            Tomcat.addServlet(ctx, "login", new LoginServlet());
            ctx.addServletMappingDecoded("/login", "login");
            Tomcat.addServlet(ctx, "metrics", new MetricsServlet());
            ctx.addServletMappingDecoded("/metrics", "metrics");

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            System.out.println("Облом!");
        }
    }

}
