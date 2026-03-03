package com.uoj.equipment.dto;

public record NewPurchaseItemDTO(
        Long equipmentId,
        int quantityRequested,
        String remark
) {}
