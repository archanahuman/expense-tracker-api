package com.learning.ExpenseTracker.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI expenseTrackerOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Expense Tracker API")

                        .description("REST API for managing daily expenses")

                        .version("1.0")

                        .contact(new Contact()
                                .name("Archana Chelmila")
                                .email("your-email@example.com")
                        )
                )

                .externalDocs(new ExternalDocumentation()

                        .description("GitHub Repository")

                        .url("https://github.com/archanahuman/expense-tracker-api"));
    }
}