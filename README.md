# DemoVanillaJava

### Описание
Демонстрационное приложение на Java без фреймворков. Взаимодействие с инфраструктурой (Postges, Kafka) осуществляется через целевые библиотеки. 
В качестве web-сервера используется встроенный Tomcat

### Сборка

Команда сборки сервиса:
```
./gradlew clean build
```

Команда сборки контейнера сервиса:
```
docker build -t demo-vanilla-java:v1 .
```

Команда запуска контейнера сервиса:
```
docker run demo-vanilla-java:v1
```

Зависимости сервиса:
- Kafka
- Redis
- PostgreSQL
- Prometheus


