package com.shyn.monkeysoft.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Optional<Department> findDepartmentByDepartmentId(String departmentId);

    public Optional<Department> findDepartmentById(Long id);
}
