package com.uoj.equipment.controller;

import com.uoj.equipment.entity.Lab;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.service.HodLabService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hod/labs")
public class HodLabController {

    private final HodLabService hodLabService;

    public HodLabController(HodLabService hodLabService) {
        this.hodLabService = hodLabService;
    }


     //HOD view all labs in own department with assigned TO .
    @GetMapping
    public ResponseEntity<List<Lab>> listLabs(Authentication auth) {
        String hodEmail = auth.getName();
        List<Lab> labs = hodLabService.listLabsForHod(hodEmail);
        return ResponseEntity.ok(labs);
    }


      //HOD assign a TO to a specific lab.
    @PostMapping("/{labId}/assign-to")
    public ResponseEntity<String> assignTo(Authentication auth,
                                           @PathVariable Long labId,
                                           @RequestParam Long toUserId) {
        String hodEmail = auth.getName();
        Lab updated = hodLabService.assignToToLab(hodEmail, labId, toUserId);

        User assignedTo = updated.getTechnicalOfficer();
        String msg = "TO " + (assignedTo != null ? assignedTo.getFullName() : "null")
                + " assigned to lab " + updated.getName() + " (id=" + updated.getId() + ")";

        return ResponseEntity.ok(msg);
    }


     //HOD clear TO assignment from a lab.
    @PostMapping("/{labId}/clear-to")
    public ResponseEntity<String> clearTo(Authentication auth,
                                          @PathVariable Long labId) {
        String hodEmail = auth.getName();
        Lab updated = hodLabService.clearToFromLab(hodEmail, labId);

        String msg = "TO assignment cleared for lab " + updated.getName()
                + " (id=" + updated.getId() + ")";
        return ResponseEntity.ok(msg);
    }
}
