package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "API Documentation",
				version = "1.0",
				description = "API documentation for the application",
				contact = @Contact(
						name = "Support",
						email = "support@example.com",
						url = "https://example.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		servers = @Server(url = "http://localhost:8080", description = "Local Server URL")
)
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("bearer-key",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.addSecurityItem(new SecurityRequirement().addList("bearer-key"))
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("API Documentation")
						.version("1.0")
						.description("This is the API documentation for the application.")
						.termsOfService("http://example.com/terms/")
						.contact(new io.swagger.v3.oas.models.info.Contact()
								.name("Support")
								.url("https://example.com")
								.email("support@example.com"))
						.license(new io.swagger.v3.oas.models.info.License()
								.name("Apache 2.0")
								.url("http://www.apache.org/licenses/LICENSE-2.0.html")));
	}
}
