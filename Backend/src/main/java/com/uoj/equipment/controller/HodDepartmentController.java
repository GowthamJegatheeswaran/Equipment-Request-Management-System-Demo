package com.uoj.equipment.controller;

import com.uoj.equipment.dto.HodDeptRequestDTO;
import com.uoj.equipment.service.HodDepartmentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hod")
public class HodDepartmentController {

    private final HodDepartmentService hodDepartmentService;

    public HodDepartmentController(HodDepartmentService hodDepartmentService) {
        this.hodDepartmentService = hodDepartmentService;
    }

    // For HOD Inventory/Report/Inspect pages (department-wide, across all labs in HOD department)
    @GetMapping("/department/requests")
    public List<HodDeptRequestDTO> departmentRequests(Authentication auth) {
        return hodDepartmentService.listDepartmentRequests(auth.getName());
    }
}
