FROM bellsoft/liberica-openjre-alpine:17.0.1
COPY build/libs/demoVanillaJava-*.jar demoVanillaJava.jar
ENTRYPOINT ["java", "-jar", "/demoVanillaJava.jar"]