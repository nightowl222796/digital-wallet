package com.wallet.digitalwallet;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("💳 PayFlow — Digital Wallet API")
                        .description("""
                                A production-ready Digital Wallet & Payment System
                                built with Spring Boot 3, MySQL, and JPA.
                                
                                Features:
                                - User Registration & Management
                                - Wallet Creation & Balance Management  
                                - Secure Money Transfers
                                - Transaction History
                                - Idempotency Protection
                                - REPEATABLE_READ Transaction Isolation
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("PayFlow Team")
                                .email("payflow@example.com"))
                        .license(new License()
                                .name("MIT License")));
    }
}