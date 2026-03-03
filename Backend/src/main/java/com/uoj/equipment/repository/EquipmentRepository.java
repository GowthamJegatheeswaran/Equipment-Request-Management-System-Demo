package com.uoj.equipment.repository;

import com.uoj.equipment.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findByLabId(Long labId);

    List<Equipment> findByLabIdAndActiveTrue(Long labId);

    List<Equipment> findByActiveTrue();
}
