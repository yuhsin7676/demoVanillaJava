package com.example.demoVanillaJava.api;

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

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            System.out.println("Облом!");
        }
    }

}
