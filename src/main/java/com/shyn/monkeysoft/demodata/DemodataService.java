package com.shyn.monkeysoft.demodata;

import com.shyn.monkeysoft.department.Department;
import com.shyn.monkeysoft.department.DepartmentRepository;
import com.shyn.monkeysoft.user.MonkeyUser;
import com.shyn.monkeysoft.user.MonkeyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemodataService {

    private final DepartmentRepository departmentRepository;
    private final MonkeyUserRepository monkeyUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DemodataService(DepartmentRepository departmentRepository, MonkeyUserRepository monkeyUserRepository, PasswordEncoder passwordEncoder) {
        this.departmentRepository = departmentRepository;
        this.monkeyUserRepository = monkeyUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createDemoData() {
        Department directorateDept = new Department("D001", "Directorate");
        Department frontendDept = new Department("D002", "Frontend Department");
        Department backendDept = new Department("D003", "Backend Department");

        departmentRepository.saveAll(List.of(directorateDept, frontendDept, backendDept));

        MonkeyUser monkeyUser1 = new MonkeyUser("admin", passwordEncoder.encode("admin"), "Boris", "Bananow", true, directorateDept);
        MonkeyUser monkeyUser2 = new MonkeyUser("dominator123", passwordEncoder.encode("dominator123"), "Vitalii", "Gorilov", true, backendDept);
        MonkeyUser monkeyUser3 = new MonkeyUser("chimp_coder", passwordEncoder.encode("chimp_coder"), "Oleg", "Chimp", true, backendDept);
        MonkeyUser monkeyUser4 = new MonkeyUser("macaque_gena", passwordEncoder.encode("macaque_gena"), "Genadi", "Macaqoff", true, frontendDept);
        MonkeyUser monkeyUser5 = new MonkeyUser("marmazette_marina", passwordEncoder.encode("marmazette_marina"), "Marina", "M", true, frontendDept);
        MonkeyUser monkeyUser6 = new MonkeyUser("marmazette_sveta", passwordEncoder.encode("marmazette_sveta"), "Svetlana", "M", true, frontendDept);

        monkeyUserRepository.saveAll(List.of(monkeyUser1, monkeyUser2, monkeyUser3, monkeyUser4, monkeyUser5, monkeyUser6));
    }
}
