package com.nandaliyan.mylibapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info= @Info(
    title = "MyLib API",
    version = "1.0.0",
    description = "MyLib API Documentation",
    contact = @Contact(
        name = "Nandaliyan Rais",
        url = "https://github.com/nandaliyanrais"
    )
))
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenAPIConfiguration {
    
}
