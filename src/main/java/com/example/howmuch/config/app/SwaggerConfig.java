package com.example.howmuch.config.app;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApI() {
        Info info = new Info()
                .title("[얼마나 프로젝트] API Document")
                .version("V_1")
                .description("[얼마나 프로젝트] API 명세서입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}