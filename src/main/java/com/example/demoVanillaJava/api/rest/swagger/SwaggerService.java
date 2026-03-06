package com.example.demoVanillaJava.api.rest.swagger;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

public class SwaggerService {

    private static final String SWAGGER_CUSTOM_UI_PATH = "swagger";
    private static final String SWAGGER_UI_PATH = "META-INF/resources/webjars/swagger-ui/4.15.5";
    private static final String INDEX_HTML = "/index.html";

    @Getter
    @Builder
    public static class Resource {
        private String resourcePath;
        private InputStream resourceStream;
    }

    public Resource getView(String pathInfo) {

        String resourcePath;

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty())
            resourcePath = SWAGGER_CUSTOM_UI_PATH + INDEX_HTML;
        else
            resourcePath = SWAGGER_UI_PATH + pathInfo;

        // Загружаем ресурс из classpath (из webjars)
        InputStream resourceStream = getClass().getClassLoader()
                .getResourceAsStream(resourcePath);

        if (resourceStream == null)
            resourceStream = getClass().getClassLoader()
                    .getResourceAsStream(SWAGGER_UI_PATH + INDEX_HTML);

        return Resource.builder()
                .resourcePath(resourcePath)
                .resourceStream(resourceStream)
                .build();
    }

}
