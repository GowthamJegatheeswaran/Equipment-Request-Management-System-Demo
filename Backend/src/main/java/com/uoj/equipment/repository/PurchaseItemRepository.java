package com.uoj.equipment.repository;

import com.uoj.equipment.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByPurchaseRequestId(Long purchaseRequestid);
}
