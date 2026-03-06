package com.example.demoVanillaJava.api.rest.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyOpenApiGenerator {

    public static OpenAPI createOpenAPI() {
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("User Management API")
                .description("""
                            API для управления пользователями
                            
                            ## Доступные endpoints:
                            * GET /user - список всех пользователей
                            * POST /user - создать нового пользователя
                            * PUT /user - обновить пользователя
                            * DELETE /user - удалить пользователя
                            """)
                .version("1.0.0")
                .contact(new Contact()
                        .name("Support Team")
                        .email("support@example.com")
                        .url("https://example.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        oas.info(info);
        oas.servers(Collections.singletonList(
                new Server()
                        .url("http://localhost:8080")
                        .description("Local development server")
        ));

        oas.tags(Arrays.asList(
                new Tag().name("user").description("Операции с пользователями"),
                new Tag().name("kafka").description("Операции с кафкой"),
                new Tag().name("redis").description("Операции с редис")
        ));

        Components components = new Components();
        components.schemas(Map.of(
                "CreateUserDto", new Schema<>()
                        .type("object")
                        .addProperties("login", new Schema<>().type("string"))
                        .addProperties("name", new Schema<>().type("string")),
                "UserDto", new Schema<>()
                        .type("object")
                        .addProperties("id", new Schema<>().type("integer").format("int64"))
                        .addProperties("login", new Schema<>().type("string"))
                        .addProperties("name", new Schema<>().type("string"))
        ));
        oas.components(components);

        PathItem userPathItem = new PathItem()
                .get(new Operation()
                        .tags(List.of("user"))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>().$ref("#/components/schemas/UserDto"))
                                                )))))
                .post(new Operation()
                        .tags(List.of("user"))
                        .requestBody(new RequestBody()
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/CreateUserDto"))
                                        )))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )))
                .put(new Operation()
                        .tags(List.of("user"))
                        .requestBody(new RequestBody()
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/UserDto"))
                                        )))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )))
                .delete(new Operation()
                        .tags(List.of("user"))
                        .addParametersItem(new Parameter()
                                .name("id")
                                .in("query")
                                .required(true)
                                .schema(new Schema<>()
                                        .type("string"))
                        )
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )));

        PathItem kafkaPathItem = new PathItem()
                .post(new Operation()
                        .tags(List.of("kafka"))
                        .requestBody(new RequestBody()
                                .content(new Content()
                                        .addMediaType("text/plain", new MediaType()
                                                .schema(new Schema<>().type("string"))
                                        )))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )));

        PathItem redisPathItem = new PathItem()
                .get(new Operation()
                        .tags(List.of("redis"))
                        .addParametersItem(new Parameter()
                                .name("key")
                                .in("query")
                                .required(true)
                                .schema(new Schema<>()
                                        .type("string"))
                        )
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )))
                .post(new Operation()
                        .tags(List.of("redis"))
                        .addParametersItem(new Parameter()
                                .name("key")
                                .in("query")
                                .required(true)
                                .schema(new Schema<>()
                                        .type("string"))
                        )
                        .addParametersItem(new Parameter()
                                .name("value")
                                .in("query")
                                .required(true)
                                .schema(new Schema<>()
                                        .type("string"))
                        )
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )))
                .put(new Operation()
                        .tags(List.of("redis"))
                        .addParametersItem(new Parameter()
                                .name("key")
                                .in("query")
                                .required(true)
                                .schema(new Schema<>()
                                        .type("string"))
                        )
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                        .description("OK")
                                )));

        oas.path("/user", userPathItem);
        oas.path("/sendIntoKafka", kafkaPathItem);
        oas.path("/redis", redisPathItem);



        return oas;
    }
}