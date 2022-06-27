package com.shyn.monkeysoft.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartmentsFromDB() {
        return departmentRepository.findAll();
    }

    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    public String validateNewDepartment(Department department) {
        Optional<Department> sameNameDept = departmentRepository.findDepartmentByDepartmentId(department.getDepartmentId());
        return null;

    }

    public void deleteDepartment(Long departmentKey) {
        Optional<Department> optDept = departmentRepository.findDepartmentById(departmentKey);
        if(optDept.isEmpty()) {
            throw new IllegalStateException("Department with key = " + departmentKey + " is not exists");
        }
        optDept.ifPresent(departmentRepository::delete);
    }

    public Page<Department> findDepartmentsPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Department> departments = departmentRepository.findAll();
        List<Department> result;

        if (departments.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, departments.size());
            result = departments.subList(startItem, toIndex);
        }

        return new PageImpl<>(result, PageRequest.of(currentPage, pageSize), departments.size());
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> optDept = departmentRepository.findDepartmentById(id);
        if(optDept.isEmpty()) {
            throw new IllegalStateException("Department with key = " + id + " is not exists");
        }

        return optDept.get();
    }

}
