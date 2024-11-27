package com.masa.api_gateway.filters;

import com.masa.api_gateway.service.AuthService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter
        extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final AuthService authService;

    public AuthenticationFilter(AuthService authService) {
        super(Config.class);
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = extractToken(exchange);

            if (token == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return authService.validateToken(token).flatMap(response -> {
                if (response.isValid()) {
                    exchange.getRequest().mutate()
                            .header("X-User-ID", String.valueOf(response.getUserId()))
                            .header("X-User-Role", response.getRole());
                    return chain.filter(exchange);
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }).onErrorResume(WebClientResponseException.class, ex -> {
                if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                return Mono.error(ex);
            });
        };
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /*
     * private boolean isPublicRoute(String path) { return Optional
     * .ofNullable(routeLocator.getRoutes() .filter(route ->
     * route.getUri().getPath().equals(path)).blockFirst()) .map(route ->
     * route.getUri().getPath().equals(path)).orElse(false); }
     */

    public static class Config {
        // Aquí puedes agregar configuraciones personalizadas
    }
}
