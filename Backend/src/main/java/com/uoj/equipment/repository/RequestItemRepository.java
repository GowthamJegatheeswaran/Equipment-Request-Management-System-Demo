package com.uoj.equipment.repository;

import com.uoj.equipment.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestItemRepository extends JpaRepository<RequestItem, Long> {
    List<RequestItem> findByRequestId(Long requestId);
}
