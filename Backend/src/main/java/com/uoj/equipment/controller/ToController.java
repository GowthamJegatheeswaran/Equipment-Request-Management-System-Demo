package com.uoj.equipment.controller;

import com.uoj.equipment.dto.RequestSummaryDTO;
import com.uoj.equipment.dto.ToApprovedRequestDTO;
import com.uoj.equipment.dto.ToIssueResponseDTO;
import com.uoj.equipment.dto.ToWaitResponseDTO;
import com.uoj.equipment.dto.ToVerifyReturnResponseDTO;
import com.uoj.equipment.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/to")
public class ToController {

    private final RequestService requestService;

    public ToController(RequestService requestService) {
        this.requestService = requestService;
    }

    // Approved requests for a given lab
    @GetMapping("/approved-requests")
    public List<ToApprovedRequestDTO> approved(Authentication auth, @RequestParam Long labId) {
        return requestService.toApprovedRequestsForLabView(auth.getName(), labId);
    }

    // Issue equipment
    @PostMapping("/requests/{id}/issue")
    public ToIssueResponseDTO issue(Authentication auth, @PathVariable Long id) {
        return requestService.toIssueDTO(auth.getName(), id);
    }

    // Issue a single request item
    @PostMapping("/request-items/{id}/issue")
    public ToIssueResponseDTO issueItem(Authentication auth, @PathVariable Long id) {
        return requestService.toIssueItemDTO(auth.getName(), id);
    }

    // Mark a single request item as waiting (with optional reason)
    @PostMapping("/request-items/{id}/wait")
    public ToWaitResponseDTO waitItem(Authentication auth,
                                     @PathVariable Long id,
                                     @RequestParam(required = false) String reason) {
        var req = requestService.toWaitItem(auth.getName(), id, reason);
        return new ToWaitResponseDTO(req.getId(), id, "WAITING_TO_ISSUE", "Item marked as waiting");
    }

    // Verify return
    @PostMapping("/requests/{id}/verify-return")
    public ToVerifyReturnResponseDTO verifyReturn(Authentication auth,
                                                  @PathVariable Long id,
                                                  @RequestParam boolean damaged) {
        return requestService.toVerifyReturnDTO(auth.getName(), id, damaged);
    }

    // Verify return for a single request item
    @PostMapping("/request-items/{id}/verify-return")
    public ToVerifyReturnResponseDTO verifyReturnItem(Authentication auth,
                                                      @PathVariable Long id,
                                                      @RequestParam boolean damaged) {
        return requestService.toVerifyReturnItemDTO(auth.getName(), id, damaged);
    }

    @GetMapping("/requests")
    public List<RequestSummaryDTO> getAllRequestsForTo(Authentication auth) {
        // auth.getName() = logged users email
        return requestService.toRequestsForTo(auth.getName());
    }

}
