package com.uoj.equipment.dto;

import com.uoj.equipment.enums.NotificationStatus;
import com.uoj.equipment.enums.NotificationType;

import java.time.Instant;

public record NotificationResponseDTO(
        Long id,
        NotificationType type,
        String title,
        String message,
        Long requestId,
        Long purchaseRequestId,
        NotificationStatus status,
        Instant createdAt
) {}
