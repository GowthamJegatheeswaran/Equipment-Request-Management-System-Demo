package com.uoj.equipment.repository;

import com.uoj.equipment.entity.PurchaseRequest;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {

    // TO can view their own purchase requests
    List<PurchaseRequest> findByToUserOrderByCreatedDateDesc(User toUser);

    // HOD can view requests waiting for them (SUBMITTED_TO_HOD)
    List<PurchaseRequest> findByHodUserAndStatusOrderByCreatedDateDesc(User hodUser,
                                                                       PurchaseStatus status);

    // HOD view all departmental purchases (any status)
    List<PurchaseRequest> findByHodUserOrderByCreatedDateDesc(User hodUser);

    // Admin view purchases waiting for admin (APPROVED_BY_HOD)
    List<PurchaseRequest> findByDepartmentAndStatusOrderByCreatedDateDesc(String department,
                                                                          PurchaseStatus status);

    // Same as above but allows querying multiple department codes (ex: CE should include COM/CSE rows)
    List<PurchaseRequest> findByDepartmentInAndStatusOrderByCreatedDateDesc(List<String> departments,
                                                                            PurchaseStatus status);

    // (Used in a few places)
    List<PurchaseRequest> findByDepartmentAndStatus(String department, PurchaseStatus status);
}
