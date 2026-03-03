package com.uoj.equipment.controller;

import com.uoj.equipment.dto.NotificationDTO;
import com.uoj.equipment.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // All roles use this (Student, Staff, Lecturer, TO, HOD, Admin)
    @GetMapping("/my")
    public List<NotificationDTO> myNotifications(Authentication auth) {
        return notificationService.getMyNotifications(auth.getName());
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id, Authentication auth) {
        notificationService.markAsRead(auth.getName(), id);
    }
}
