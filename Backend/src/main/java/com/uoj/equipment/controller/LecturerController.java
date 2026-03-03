package com.uoj.equipment.controller;

import com.uoj.equipment.dto.RequestSummaryDTO;
import com.uoj.equipment.dto.StudentMyRequestDTO;
import com.uoj.equipment.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecturer")
public class LecturerController {

    private final RequestService requestService;

    public LecturerController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/my-requests")
    public List<StudentMyRequestDTO> lecturerMyRequests(Authentication auth) {
        return requestService.myRequestsStudentView(auth.getName());
    }


    @GetMapping("/approval-queue")
    public List<RequestSummaryDTO> queue(Authentication auth) {
        return requestService.lecturerQueueDTO(auth.getName());
    }

    @PostMapping("/requests/{id}/approve")
    public RequestSummaryDTO approve(Authentication auth, @PathVariable Long id) {
        return requestService.lecturerApproveDTO(auth.getName(), id);
    }

    // Per-item approve (one equipment line)
    @PostMapping("/request-items/{id}/approve")
    public RequestSummaryDTO approveItem(Authentication auth, @PathVariable Long id) {
        return requestService.lecturerApproveItemDTO(auth.getName(), id);
    }

    @PostMapping("/requests/{id}/reject")
    public RequestSummaryDTO reject(Authentication auth,
                                    @PathVariable Long id,
                                    @RequestParam(required = false) String reason) {
        return requestService.lecturerRejectDTO(auth.getName(), id, reason);
    }

    // Per-item reject (one equipment line)
    @PostMapping("/request-items/{id}/reject")
    public RequestSummaryDTO rejectItem(Authentication auth,
                                        @PathVariable Long id,
                                        @RequestParam(required = false) String reason) {
        return requestService.lecturerRejectItemDTO(auth.getName(), id, reason);
    }
}
