package com.uoj.equipment.service;

import com.uoj.equipment.config.JwtUtil;
import com.uoj.equipment.dto.LoginDTO;
import com.uoj.equipment.dto.SignupRequestDTO;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Signup ONLY for STUDENT and STAFF
    public Map<String, Object> signup(SignupRequestDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank())
            throw new IllegalArgumentException("Email required");
        if (dto.getPassword() == null || dto.getPassword().isBlank())
            throw new IllegalArgumentException("Password required");
        if (dto.getDepartment() == null || dto.getDepartment().isBlank())
            throw new IllegalArgumentException("Department required");
        if (dto.getRole() == null || dto.getRole().isBlank())
            throw new IllegalArgumentException("Role required");


        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists");
        if ("STUDENT".equals(dto.getRole()) && dto.getRegNo() != null) {
            if (userRepository.findByRegNo(dto.getRegNo()).isPresent()) {
                throw new IllegalArgumentException("Registration number already exists");
            }
        }


        Role role;
        try {
            role = Role.valueOf(dto.getRole().trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid role");
        }

        if (role != Role.STUDENT && role != Role.STAFF) {
            throw new IllegalArgumentException("Signup allowed only for STUDENT/STAFF");
        }

        if (role == Role.STUDENT) {
            if (dto.getRegNo() == null || dto.getRegNo().isBlank())
                throw new IllegalArgumentException("Reg No required for student");
        }

        User u = new User();
        u.setEmail(dto.getEmail().trim().toLowerCase());
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        u.setRole(role);
        u.setDepartment(com.uoj.equipment.util.DepartmentUtil.normalize(dto.getDepartment()));
        u.setEnabled(true);
        u.setRegNo(role == Role.STUDENT ? dto.getRegNo() : null);
        u.setFullName(dto.getFullName());

        userRepository.save(u);

        return Map.of(
                "message", "Signup successful",
                "email", u.getEmail(),
                "role", u.getRole().name()
        );
    }

    public Map<String, Object> login(LoginDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtUtil.generateToken(auth.getName());

        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        return Map.of(
                "token", token,
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "department", user.getDepartment()
        );
    }
}
