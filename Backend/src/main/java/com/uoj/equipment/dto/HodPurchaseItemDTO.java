package com.uoj.equipment.dto;

public record HodPurchaseItemDTO(
        Long equipmentId,
        String equipmentName,
        int quantityRequested,
        String remark
) {}
