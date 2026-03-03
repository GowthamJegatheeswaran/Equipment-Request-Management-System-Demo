package com.uoj.equipment.dto;

public record ToWaitResponseDTO(
        Long requestId,
        Long requestItemId,
        String itemStatus,
        String message
) {}
