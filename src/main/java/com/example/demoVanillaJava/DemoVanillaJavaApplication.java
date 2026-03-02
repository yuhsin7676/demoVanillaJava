package com.example.demoVanillaJava;

import com.example.demoVanillaJava.api.TomcatService;
import com.example.demoVanillaJava.spi.LiquibaseService;

public class DemoVanillaJavaApplication {

    public static void main(String[] args) {

        // 1. Запускаем миграцию ДБ
        new LiquibaseService().migration();

        // 2. Запускаем веб-сервер
        new TomcatService().start();
        
        
    }

}
