package com.masa.api_gateway.service;

import com.masa.api_gateway.dto.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://auth-service:8093/api/v1/auth").build();
    }

    public Mono<ValidationResponse> validateToken(String token) {
        return webClient.post().uri("/validate").bodyValue(token).retrieve()
                .bodyToMono(ValidationResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        return Mono.just(
                                ValidationResponse.builder()
                                        .valid(false)
                                        .message("Unauthorized")
                                        .expiration(0L)
                                        .permissions(null)
                                        .role(null)
                                        .userId(0)
                                        .username(null)
                                        .build()
                        );
                    }
                    return Mono.error(ex);
                });
    }
}
