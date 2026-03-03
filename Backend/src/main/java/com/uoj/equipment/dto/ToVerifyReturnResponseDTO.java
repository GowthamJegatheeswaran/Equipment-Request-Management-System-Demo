package com.uoj.equipment.dto;

import java.util.List;

public record ToVerifyReturnResponseDTO(
        Long requestId,
        String status,
        String message,
        boolean damaged,
        List<VerifiedReturnItemDTO> items
) {}
