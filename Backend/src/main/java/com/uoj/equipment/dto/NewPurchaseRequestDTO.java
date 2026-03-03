package com.uoj.equipment.dto;

import java.util.List;

public record NewPurchaseRequestDTO(
        String reason,
        List<NewPurchaseItemDTO> items
) {}
