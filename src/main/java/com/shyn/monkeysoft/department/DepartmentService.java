package com.shyn.monkeysoft.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    public void deleteById(Long departmentKey) {
        Optional<Department> optDept = departmentRepository.findDepartmentById(departmentKey);
        if(optDept.isEmpty()) {
            throw new IllegalStateException("Department with key = " + departmentKey + " is not exists");
        }
        Department department = optDept.get();

        if(department.getMonkeyUsers().size() > 0) {
            throw new IllegalStateException("Can`t remove department where still working monkeys!");
        }

        departmentRepository.delete(department);
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> optDept = departmentRepository.findDepartmentById(id);
        if(optDept.isEmpty()) {
            throw new IllegalStateException("Department with key = " + id + " is not exists");
        }

        return optDept.get();
    }

    public Page<Department> getDepartmentsBySearchParams(String departmentCode, String departmentDesc, Pageable paging) {
        String departmentCodeLike = departmentCode == null ? "%" : "%" + departmentCode + "%";
        String departmentDescLike = departmentDesc == null ? "%" : "%" + departmentDesc + "%";

        return departmentRepository.findAllByDepartmentIdLikeIgnoreCaseAndDescriptionLikeIgnoreCase(departmentCodeLike, departmentDescLike, paging);
    }

}
