package com.uoj.equipment.repository;

import com.uoj.equipment.entity.Notification;
import com.uoj.equipment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedDateDesc(User user);
}
