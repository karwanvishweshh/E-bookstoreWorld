package com.bookstore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info=@Info(title="Online BookStore API",version="1.0.0",description="API Documentation for the Online BookStore Project"))
public class SwaggerConfig {

}
