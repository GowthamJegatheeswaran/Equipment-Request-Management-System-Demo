package com.uoj.equipment.repository;

import com.uoj.equipment.entity.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabRepository extends JpaRepository<Lab, Long> {
    List<Lab> findByDepartment(String department);

    List<Lab> findByDepartmentOrderByIdAsc(String department);

    List<Lab> findByTechnicalOfficerId(Long toId);
}
