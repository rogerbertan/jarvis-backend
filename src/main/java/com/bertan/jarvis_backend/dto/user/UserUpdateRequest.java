package com.bertan.jarvis_backend.dto.user;

import jakarta.validation.constraints.Email;

public record UserUpdateRequest(
        @Email(message = "Email should be valid")
        String email,

        String fullName,

        String avatarUrl
) {
}
