package com.uoj.equipment.controller;

import com.uoj.equipment.dto.HodPurchaseRequestDTO;
import com.uoj.equipment.dto.NewPurchaseRequestDTO;
import com.uoj.equipment.dto.PurchaseDecisionDTO;
import com.uoj.equipment.dto.PurchaseRequestSummaryDTO;
import com.uoj.equipment.service.PurchaseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    //hod see TO request
    @GetMapping("/hod/purchase-requests")
    public List<HodPurchaseRequestDTO> viewPending(Authentication auth) {
        return purchaseService.hodViewPending(auth.getName());
    }

    // TO -> create purchase request to HOD
    @PostMapping("/to/purchase-requests")
    public PurchaseRequestSummaryDTO submitToHod(@RequestBody NewPurchaseRequestDTO dto,
                                                 Authentication auth) {
        return purchaseService.submitToHod(auth.getName(), dto);
    }
    // TO -> view own purchase requests (history)
    @GetMapping("/to/purchase-requests/my")
    public List<PurchaseRequestSummaryDTO> toMyRequests(Authentication auth) {
        return purchaseService.toMyPurchaseRequests(auth.getName());
    }

    // HOD -> view all purchase requests in their department (summary)
    @GetMapping("/hod/purchase-requests/my")
    public List<PurchaseRequestSummaryDTO> hodMyRequests(Authentication auth) {
        return purchaseService.hodMyPurchaseRequests(auth.getName());
    }

    // HOD -> approve / reject purchase
    @PostMapping("/hod/purchase-requests/{id}/decision")
    public PurchaseRequestSummaryDTO hodDecision(@PathVariable Long id,
                                                 @RequestBody PurchaseDecisionDTO dto,
                                                 Authentication auth) {
        return purchaseService.hodDecision(auth.getName(), id, dto.approve(), dto.comment());
    }

    // ADMIN -> approve / reject purchase
    @PostMapping("/admin/purchase-requests/{id}/decision")
    public PurchaseRequestSummaryDTO adminDecision(@PathVariable Long id,
                                                   @RequestBody PurchaseDecisionDTO dto,
                                                   Authentication auth) {
        // Legacy endpoint: issuedDate not provided here, defaults to today when approve=true
        return purchaseService.adminDecision(auth.getName(), id, dto.approve(), dto.comment(), null);
    }

    @GetMapping("/to/purchase-requests")
    public List<PurchaseRequestSummaryDTO> toMyPurchaseRequests(Authentication auth) {
        return purchaseService.toMyPurchaseRequests(auth.getName());
    }

    // HOD -> confirm received items after Admin issue
    @PostMapping("/hod/purchase-requests/{id}/receive")
    public PurchaseRequestSummaryDTO hodConfirmReceived(@PathVariable Long id, Authentication auth) {
        return purchaseService.hodConfirmReceived(auth.getName(), id);
    }
}
