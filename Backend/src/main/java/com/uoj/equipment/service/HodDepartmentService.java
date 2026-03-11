package com.uoj.equipment.service;

import com.uoj.equipment.dto.HodDeptRequestDTO;
import com.uoj.equipment.dto.HodDeptRequestItemDTO;
import com.uoj.equipment.entity.EquipmentRequest;
import com.uoj.equipment.entity.Lab;
import com.uoj.equipment.entity.RequestItem;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.EquipmentRequestRepository;
import com.uoj.equipment.repository.LabRepository;
import com.uoj.equipment.repository.RequestItemRepository;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HodDepartmentService {

    private final UserRepository userRepository;
    private final LabRepository labRepository;
    private final EquipmentRequestRepository equipmentRequestRepository;
    private final RequestItemRepository requestItemRepository;

    public HodDepartmentService(UserRepository userRepository,
                               LabRepository labRepository,
                               EquipmentRequestRepository equipmentRequestRepository,
                               RequestItemRepository requestItemRepository) {
        this.userRepository = userRepository;
        this.labRepository = labRepository;
        this.equipmentRequestRepository = equipmentRequestRepository;
        this.requestItemRepository = requestItemRepository;
    }

    public List<HodDeptRequestDTO> listDepartmentRequests(String hodEmail) {
        User hod = userRepository.findByEmail(hodEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid HOD"));
        if (hod.getRole() != Role.HOD) {
            throw new IllegalArgumentException("Only HOD can access department requests");
        }

        List<Lab> labs = labRepository.findByDepartmentOrderByIdAsc(hod.getDepartment());
        List<Long> labIds = labs.stream().map(Lab::getId).toList();
        if (labIds.isEmpty()) return List.of();

        List<EquipmentRequest> requests = equipmentRequestRepository.findByLabIdInOrderByIdDesc(labIds);
        List<HodDeptRequestDTO> out = new ArrayList<>();

        for (EquipmentRequest r : requests) {
            List<RequestItem> items = requestItemRepository.findByRequestId(r.getId());
            List<HodDeptRequestItemDTO> itemDTOs = items.stream()
                    .map(it -> new HodDeptRequestItemDTO(
                            it.getId(),
                            it.getEquipment().getId(),
                            it.getEquipment().getName(),
                            it.getQuantity(),
                            it.getIssuedQty(),
                            it.isReturned(),
                            it.isDamaged(),
                            String.valueOf(it.getStatus())
                    ))
                    .toList();

            User req = r.getRequester();

            // ── requesterRole: human-readable label ────────────────────────
            String roleLabel = null;
            if (req != null && req.getRole() != null) {
                roleLabel = switch (req.getRole()) {
                    case STUDENT  -> "Student";
                    case STAFF    -> "Staff";
                    case LECTURER -> "Lecturer";
                    case HOD      -> "HOD";
                    case TO       -> "Technical Officer";
                    case ADMIN    -> "Admin";
                };
            }

            out.add(new HodDeptRequestDTO(
                    r.getId(),
                    r.getLab().getId(),
                    r.getLab().getName(),
                    req != null ? req.getId()       : null,
                    req != null ? req.getFullName() : null,
                    req != null ? req.getRegNo()    : null,
                    roleLabel,                              // ← requesterRole populated
                    String.valueOf(r.getStatus()),
                    String.valueOf(r.getPurpose()),
                    r.getFromDate(),
                    r.getToDate(),
                    itemDTOs
            ));
        }

        return out;
    }
}