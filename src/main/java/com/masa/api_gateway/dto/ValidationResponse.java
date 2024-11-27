package com.masa.api_gateway.dto;

import java.util.Set;

public class ValidationResponse {
    private boolean valid;
    private long userId;
    private String username;
    private String role;
    private Set<String> permissions;
    private String message;

    public ValidationResponse() {}

    public ValidationResponse(boolean valid, long userId, String username, String role,
            Set<String> permissions, String message) {
        this.valid = valid;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.permissions = permissions;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
