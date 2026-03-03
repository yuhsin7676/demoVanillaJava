package com.example.demoVanillaJava.api.rest;

import com.example.demoVanillaJava.api.rest.servlets.KafkaServlet;
import com.example.demoVanillaJava.api.rest.servlets.RedisServlet;
import com.example.demoVanillaJava.api.rest.servlets.UserServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

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

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            System.out.println("Облом!");
        }
    }

}
