package com.uoj.equipment.dto;

public record UserPublicDTO(
        Long id,
        String email,
        String fullName,
        String role,
        String department
) {}
