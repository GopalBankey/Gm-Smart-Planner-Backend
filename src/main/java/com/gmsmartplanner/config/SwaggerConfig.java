package com.gmsmartplanner.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        final String securitySchemeName =
                "bearerAuth";

        return new OpenAPI()

                .info(

                        new Info()

                                .title(
                                        "GM Smart Planner API"
                                )

                                .version(
                                        "1.0"
                                )

                                .description(
                                        "Todo + Budget Management REST APIs"
                                )

                                .contact(

                                        new Contact()

                                                .name(
                                                        "GM Smart Planner"
                                                )
                                )
                )

                .addSecurityItem(

                        new SecurityRequirement()

                                .addList(
                                        securitySchemeName
                                )
                )

                .components(

                        new Components()

                                .addSecuritySchemes(

                                        securitySchemeName,

                                        new SecurityScheme()

                                                .name(
                                                        securitySchemeName
                                                )

                                                .type(
                                                        SecurityScheme.Type.HTTP
                                                )

                                                .scheme(
                                                        "bearer"
                                                )

                                                .bearerFormat(
                                                        "JWT"
                                                )
                                )
                );
    }
}