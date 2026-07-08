package com.sigcqal.api.web.Auth.Dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Integer id, String username, String email,
        Set<String> roles, Boolean enabled, LocalDateTime createdAt) {
}