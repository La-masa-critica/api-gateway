package com.masa.api_gateway.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponse {
    @Getter
    private boolean valid;
    private long userId;
    private String username;
    private String role;
    private Set<String> permissions;
    private Long expiration;
    private String message;
}
