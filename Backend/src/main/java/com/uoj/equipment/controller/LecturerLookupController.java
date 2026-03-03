package com.uoj.equipment.controller;

import com.uoj.equipment.dto.UserPublicDTO;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common")
public class LecturerLookupController {

    private final UserRepository userRepository;

    public LecturerLookupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/lecturers")
    public List<UserPublicDTO> lecturers(@RequestParam String department) {
        return userRepository.findByRoleAndDepartmentInOrderByFullNameAsc(Role.LECTURER, com.uoj.equipment.util.DepartmentUtil.aliasesForQuery(department))
                .stream()
                .map(u -> new UserPublicDTO(
                        u.getId(),
                        u.getEmail(),
                        u.getFullName(),
                        u.getRole().name(),
                        u.getDepartment()
                ))
                .toList();
    }
}
