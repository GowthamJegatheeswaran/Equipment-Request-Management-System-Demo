package com.uoj.equipment.service;

import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


     //name@eng.jfn.ac.lk

    private void validateStaffEmail(String email) {
        String staffEmailRegex = "^[a-zA-Z0-9._]+@eng\\.jfn\\.ac\\.lk$";

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!email.matches(staffEmailRegex)) {
            throw new IllegalArgumentException(
                    "Invalid staff/lecturer email. It must be like: name@eng.jfn.ac.lk"
            );
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }


     //Admin creates a NON-STUDENT user:
     //LECTURER, STAFF, HOD, TO, ADMIN.

    @Transactional
    public User createStaffUser(String fullName,
                                String email,
                                String department,
                                Role role,
                                String encodedPassword) {

        if (role == Role.STUDENT) {
            throw new IllegalArgumentException("Use student signup endpoint to create students.");
        }

        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Department is required");
        }

        validateStaffEmail(email);

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setDepartment(com.uoj.equipment.util.DepartmentUtil.normalize(department));
        user.setPasswordHash(encodedPassword);
        user.setRole(role);
        user.setEnabled(true);

        return userRepository.save(user);
    }


     //Admin updates user details except password
     // For NON-STUDENT: can update name, email, department, role, enabled.
      //For STUDENT: modification is not allowed here

    @Transactional
    public User updateUserDetails(Long userId,
                                  String fullName,
                                  String email,
                                  String department,
                                  Role role,
                                  Boolean enabled) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Students: no field modifications from admin panel (only enable/disable/remove separately)
        if (user.getRole() == Role.STUDENT) {
            // You can relax this later if you want to allow department/enable changes
            throw new IllegalArgumentException(
                    "Cannot modify student details via admin. Only removal/enable/disable allowed."
            );
        }

        // Full name update
        if (fullName != null && !fullName.isBlank()) {
            user.setFullName(fullName);
        }

        // Department update
        if (department != null && !department.isBlank()) {
            user.setDepartment(com.uoj.equipment.util.DepartmentUtil.normalize(department));
        }

        // Role update (but never set to STUDENT from here)
        if (role != null && role != Role.STUDENT) {
            user.setRole(role);
        }

        // Email update with staff-style validation
        if (email != null && !email.isBlank()) {

            // If same email as before, skip validation
            if (!email.equalsIgnoreCase(user.getEmail())) {
                validateStaffEmail(email);
                user.setEmail(email);
            }
        }

        // Enabled flag update
        if (enabled != null) {
            user.setEnabled(enabled);
        }

        return userRepository.save(user);
    }


     // Enable / disable a user.

    @Transactional
    public User setUserEnabled(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEnabled(enabled);
        return userRepository.save(user);
    }


     //Delete a user students or staff

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
