package com.masa.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// Permitir CORS desde cualquier origen con un patrón
		registry.addMapping("/**")
				.allowedOriginPatterns("*") // Usar el patrón para permitir todos los orígenes
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
				.allowedHeaders("*") // Permitir todos los encabezados
				.allowCredentials(true); // Permitir credenciales
	}
}
