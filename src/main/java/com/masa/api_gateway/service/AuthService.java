package com.masa.api_gateway.service;

import com.masa.api_gateway.dto.ValidationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://auth-service").build();
    }

    public Mono<ValidationResponse> validateToken(String token) {
        return webClient.post()
                .uri("/validate")
                .bodyValue(token)
                .retrieve()
                .bodyToMono(ValidationResponse.class);
    }
}