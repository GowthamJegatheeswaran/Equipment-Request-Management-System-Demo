package com.uoj.equipment.controller;

import com.uoj.equipment.dto.CreateRequestResponseDTO;
import com.uoj.equipment.dto.NewRequestDTO;
import com.uoj.equipment.dto.RequestSummaryDTO;
import com.uoj.equipment.dto.StudentAcceptanceDTO;
import com.uoj.equipment.dto.StudentMyRequestDTO;
import com.uoj.equipment.dto.StudentReturnDTO;
import com.uoj.equipment.service.RequestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // Student/Staff/Lecturer can call this endpoint; response is minimal.
    @PostMapping("/requests")
    public CreateRequestResponseDTO create(Authentication auth, @RequestBody NewRequestDTO dto) {
        RequestSummaryDTO saved = requestService.createRequestAndReturnDTO(auth.getName(), dto);
        return new CreateRequestResponseDTO(saved.requestId(), saved.status(), "Request submitted successfully");
    }

    // Student/Staff My Requests (clean card view)
    @GetMapping("/requests")
    public List<StudentMyRequestDTO> my(Authentication auth) {
        return requestService.myRequestsStudentView(auth.getName());
    }

    // Accept issue
    @PostMapping("/requests/{id}/accept-issue")
    public StudentAcceptanceDTO acceptIssue(Authentication auth, @PathVariable Long id) {
        return requestService.studentAcceptIssueDTO(auth.getName(), id);
    }

    // Accept issue for a single request item
    @PostMapping("/request-items/{id}/accept-issue")
    public StudentAcceptanceDTO acceptIssueItem(Authentication auth, @PathVariable Long id) {
        return requestService.studentAcceptIssueItemDTO(auth.getName(), id);
    }

    // Submit return
    @PostMapping("/requests/{id}/return")
    public StudentReturnDTO submitReturn(Authentication auth, @PathVariable Long id) {
        return requestService.submitReturnDTO(auth.getName(), id);
    }

    // Submit return for a single request item
    @PostMapping("/request-items/{id}/return")
    public StudentReturnDTO submitReturnItem(Authentication auth, @PathVariable Long id) {
        return requestService.submitReturnItemDTO(auth.getName(), id);
    }
}
