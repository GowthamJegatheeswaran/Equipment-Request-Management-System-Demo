package com.uoj.equipment.repository;

import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDepartmentAndRole(String department, Role role);

    Optional<User> findByRegNo(String regNo);


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByRoleAndDepartment(Role role, String department);
    boolean existsByRegNo(String regNo);

    List<User> findByDepartmentOrderByFullNameAsc(String department);

    List<User> findByDepartmentAndRoleOrderByFullNameAsc(String department, Role role);

    // For legacy department aliases (e.g., CE may include COM/CSE)
    List<User> findByDepartmentInAndRoleOrderByFullNameAsc(List<String> departments, Role role);

    List<User> findByRoleAndDepartmentInOrderByFullNameAsc(Role role, List<String> departments);

}
