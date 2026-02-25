package com.example.pizzaorders.config;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI pizzaOrdersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pizza Orders API")
                        .description("REST API for managing pizza orders powered by LittleHorse workflows")
                        .version("1.0.0"));
    }
}
