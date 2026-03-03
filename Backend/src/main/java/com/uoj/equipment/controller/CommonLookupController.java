package com.uoj.equipment.controller;

import com.uoj.equipment.dto.EquipmentPublicDTO;
import com.uoj.equipment.dto.LabDTO;
import com.uoj.equipment.entity.Equipment;
import com.uoj.equipment.entity.Lab;
import com.uoj.equipment.repository.EquipmentRepository;
import com.uoj.equipment.repository.LabRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common")
public class CommonLookupController {

    private final LabRepository labRepository;
    private final EquipmentRepository equipmentRepository;

    public CommonLookupController(LabRepository labRepository, EquipmentRepository equipmentRepository) {
        this.labRepository = labRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping("/labs")
    public List<LabDTO> labs(@RequestParam(required = false) String department) {
        List<Lab> labs;
        if (department == null || department.trim().isEmpty()) {
            labs = labRepository.findAll();
        } else {
            labs = labRepository.findByDepartmentOrderByIdAsc(com.uoj.equipment.util.DepartmentUtil.normalize(department));
        }

        return labs.stream()
                .map(l -> new LabDTO(
                        l.getId(),
                        l.getName(),
                        l.getDepartment(),
                        l.getTechnicalOfficer() != null ? l.getTechnicalOfficer().getId() : null
                ))
                .toList();
    }

    @GetMapping("/equipment")
    public List<EquipmentPublicDTO> equipmentByLab(@RequestParam Long labId,
                                                   @RequestParam(defaultValue = "true") boolean activeOnly) {
        List<Equipment> list = activeOnly
                ? equipmentRepository.findByLabIdAndActiveTrue(labId)
                : equipmentRepository.findByLabId(labId);

        return list.stream()
                .map(e -> new EquipmentPublicDTO(
                        e.getId(),
                        e.getName(),
                        e.getCategory(),
                        e.getItemType(),
                        e.getTotalQty(),
                        e.getAvailableQty(),
                        e.isActive(),
                        e.getLab() != null ? e.getLab().getId() : null
                ))
                .toList();
    }
}
