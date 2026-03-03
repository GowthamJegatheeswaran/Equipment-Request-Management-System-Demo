package com.uoj.equipment.dto;

public record IssuedItemDTO(
        Long equipmentId,
        String equipmentName,
        Integer issuedQty,
        String itemType
) {}
