package com.uoj.equipment.dto;

public record VerifiedReturnItemDTO(
        Long equipmentId,
        String equipmentName,
        Integer issuedQty,
        boolean damaged,
        String itemType
) {}
