package com.example.demoVanillaJava;

import com.example.demoVanillaJava.api.kafka.listeners.MyTopicListener;
import com.example.demoVanillaJava.api.rest.TomcatService;
import com.example.demoVanillaJava.spi.sql.LiquibaseService;
import redis.clients.jedis.Jedis;

public class DemoVanillaJavaApplication {

    public static void main(String[] args) {

        // 1. Запускаем миграцию ДБ
        new LiquibaseService().migration();

        // 2. Создаём кафка-слушателя
        new MyTopicListener();

        // 3. Запускаем веб-сервер
        new TomcatService().start();
        
    }

}
