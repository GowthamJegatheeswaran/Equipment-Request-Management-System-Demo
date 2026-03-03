package com.uoj.equipment.dto;

public record StudentMyRequestItemDTO(
        Long requestItemId,
        Long equipmentId,
        String equipmentName,
        Integer quantity,
        String itemType,
        String itemStatus,
        String toWaitReason,
        Integer issuedQty,
        Boolean returned,
        Boolean damaged
) {}
