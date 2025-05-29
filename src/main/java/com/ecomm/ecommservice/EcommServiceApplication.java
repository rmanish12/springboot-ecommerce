package com.ecomm.ecommservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Ecommerce Application"))
public class EcommServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommServiceApplication.class, args);
    }

}
