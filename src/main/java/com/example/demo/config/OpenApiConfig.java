package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Server server = new Server()
                .url("https://9034.408procr.amypo.ai/")
                .description("Production Server");

        return new OpenAPI()
                .addServersItem(server)
                .components(
                        new Components().addSecuritySchemes("bearerAuth", securityScheme)
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }
}