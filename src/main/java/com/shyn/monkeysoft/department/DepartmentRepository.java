package com.shyn.monkeysoft.department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findDepartmentById(Long id);

    Page<Department> findAllByDepartmentIdLikeIgnoreCaseAndDescriptionLikeIgnoreCase(String departmentCode, String departmentDesc, Pageable pageable);

    List<Department> findAllByDepartmentIdLikeIgnoreCase(String departmentCode);

    List<Department> findAllByDescriptionLikeIgnoreCase(String departmentDesc);
}
