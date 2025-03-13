package com.mindfire.backend.dto.response;

import com.mindfire.backend.entity.Role;

public record UserResponseDto(
        String firstName,
        String lastName,
        String email,
        String role) {
}
