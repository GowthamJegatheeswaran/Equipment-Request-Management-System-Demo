package com.uoj.equipment.dto;

public record CreateRequestResponseDTO(
        Long requestId,
        String status,
        String message
) {}
