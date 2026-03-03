package com.uoj.equipment.service;

import com.uoj.equipment.dto.NotificationDTO;
import com.uoj.equipment.entity.Notification;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.NotificationType;
import com.uoj.equipment.repository.NotificationRepository;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // Generic method to create notification
    public void notifyUser(User user,
                           NotificationType type,
                           String title,
                           String message,
                           Long relatedRequestId,
                           Long relatedPurchaseId) {

        Notification n = new Notification();
        n.setUser(user);
        n.setType(type);
        n.setTitle(title);
        n.setMessage(message);
        n.setRelatedRequestId(relatedRequestId);
        n.setRelatedPurchaseId(relatedPurchaseId);
        n.setCreatedDate(LocalDateTime.now());
        n.setReadFlag(false);

        notificationRepository.save(n);
    }

    // For /api/notifications/my
    public List<NotificationDTO> getMyNotifications(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        return notificationRepository.findByUserOrderByCreatedDateDesc(user)
                .stream()
                .map(n -> new NotificationDTO(
                        n.getId(),
                        n.getType(),
                        n.getTitle(),
                        n.getMessage(),
                        n.getRelatedRequestId(),
                        n.getRelatedPurchaseId(),
                        n.getCreatedDate(),
                        n.isReadFlag()
                ))
                .toList();
    }

    // Optional mark a notification as read
    public void markAsRead(String email, Long notificationId) {
        Notification n = notificationRepository.findById(notificationId).orElseThrow();
        if (!n.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("Not your notification");
        }
        n.setReadFlag(true);
        notificationRepository.save(n);
    }
}
