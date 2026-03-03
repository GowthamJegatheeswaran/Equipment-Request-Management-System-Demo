package com.uoj.equipment.service;

import com.uoj.equipment.entity.Lab;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.LabRepository;
import com.uoj.equipment.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HodLabService {

    private final UserRepository userRepository;
    private final LabRepository labRepository;

    public HodLabService(UserRepository userRepository,
                         LabRepository labRepository) {
        this.userRepository = userRepository;
        this.labRepository = labRepository;
    }


     //List all labs for this HOD's department, including assigned TO if any.

    @Transactional(readOnly = true)
    public List<Lab> listLabsForHod(String hodEmail) {
        User hod = userRepository.findByEmail(hodEmail)
                .orElseThrow(() -> new IllegalArgumentException("HOD not found"));

        if (hod.getRole() != Role.HOD) {
            throw new IllegalArgumentException("Only HOD can view department labs");
        }

        // HOD sees only labs of their department
        return labRepository.findByDepartmentOrderByIdAsc(hod.getDepartment());
    }


     // Assign a TO (technical officer) to a lab.
     /* Constraints:
     *  Caller must be HOD
     *  Lab department must match HOD department
     *  TO user must have role - TO and same department
     */
    @Transactional
    public Lab assignToToLab(String hodEmail, Long labId, Long toUserId) {
        User hod = userRepository.findByEmail(hodEmail)
                .orElseThrow(() -> new IllegalArgumentException("HOD not found"));

        if (hod.getRole() != Role.HOD) {
            throw new IllegalArgumentException("Only HOD can assign TO to labs");
        }

        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new IllegalArgumentException("Lab not found"));

        // Department check: HOD can only manage labs in own department
        if (lab.getDepartment() == null || hod.getDepartment() == null ||
                !lab.getDepartment().equalsIgnoreCase(hod.getDepartment())) {
            throw new IllegalArgumentException("Cannot assign TO for lab in another department");
        }

        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("TO user not found"));

        if (toUser.getRole() != Role.TO) {
            throw new IllegalArgumentException("Selected user is not a TO");
        }

        // TO must belong to same department as lab/HOD
        if (toUser.getDepartment() == null ||
                !toUser.getDepartment().equalsIgnoreCase(hod.getDepartment())) {
            throw new IllegalArgumentException("TO department mismatch with HOD/lab department");
        }

        lab.setTechnicalOfficer(toUser);
        return labRepository.save(lab);
    }


     //Clear TO assignment from a lab (no assigned TO).

    @Transactional
    public Lab clearToFromLab(String hodEmail, Long labId) {
        User hod = userRepository.findByEmail(hodEmail)
                .orElseThrow(() -> new IllegalArgumentException("HOD not found"));

        if (hod.getRole() != Role.HOD) {
            throw new IllegalArgumentException("Only HOD can clear TO assignment");
        }

        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new IllegalArgumentException("Lab not found"));

        if (lab.getDepartment() == null || hod.getDepartment() == null ||
                !lab.getDepartment().equalsIgnoreCase(hod.getDepartment())) {
            throw new IllegalArgumentException("Cannot clear TO for lab in another department");
        }

        lab.setTechnicalOfficer(null);
        return labRepository.save(lab);
    }
}
