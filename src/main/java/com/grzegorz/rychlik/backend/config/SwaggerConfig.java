package com.grzegorz.rychlik.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalTime.class,String.class)
                .directModelSubstitute(LocalDate.class,String.class)
                .directModelSubstitute(LocalDateTime.class,String.class);
    }
}
