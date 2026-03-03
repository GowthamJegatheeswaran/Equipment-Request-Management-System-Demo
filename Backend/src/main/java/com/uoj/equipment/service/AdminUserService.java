package com.uoj.equipment.service;

import com.uoj.equipment.dto.CreateUserRequestDTO;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // CREATE USER WITH A ROLE
    @Transactional
    public User createUserWithRole(CreateUserRequestDTO dto, Role role) {

        // check duplicate email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }

        User u = new User();
        u.setFullName(dto.getFullName());
        u.setEmail(dto.getEmail().trim().toLowerCase());
        u.setDepartment(com.uoj.equipment.util.DepartmentUtil.normalize(dto.getDepartment()));
        u.setRole(role);

        // Student reg no requirement
        if (role == Role.STUDENT) {
            if (dto.getRegNo() == null || dto.getRegNo().isBlank()) {
                throw new IllegalArgumentException("regNo required for STUDENT");
            }
            u.setRegNo(dto.getRegNo());
        }

        // Default password if not provided
        String password = (dto.getInitialPassword() == null || dto.getInitialPassword().isBlank())
                ? "Default@123"
                : dto.getInitialPassword();

        u.setPasswordHash(passwordEncoder.encode(password));
        u.setEnabled(true);

        return userRepository.save(u);
    }


    // UPDATE USER BASIC INFO
    @Transactional
    public User updateUserBasicInfo(Long id, CreateUserRequestDTO dto) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (dto.getFullName() != null) u.setFullName(dto.getFullName());
        if (dto.getEmail() != null) u.setEmail(dto.getEmail().trim().toLowerCase());
        if (dto.getDepartment() != null) u.setDepartment(com.uoj.equipment.util.DepartmentUtil.normalize(dto.getDepartment()));

        if (u.getRole() == Role.STUDENT && dto.getRegNo() != null) {
            u.setRegNo(dto.getRegNo());
        }

        if (dto.getEnabled() != null) u.setEnabled(dto.getEnabled());

        return userRepository.save(u);
    }

    // STUDENT -> delete
    // others -> disable
    @Transactional
    public void deleteOrDisableUser(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (u.getRole() == Role.STUDENT) {
            userRepository.delete(u);
        } else {
            u.setEnabled(false);
            userRepository.save(u);
        }
    }
}
