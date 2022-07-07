package com.shyn.monkeysoft.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String showDepartments() {
        return "departments";
    }

    @GetMapping({"/department/{id}", "/department"})
    public String editDepartment(@PathVariable(required = false) Long id, Model model) {
        Department department = id == null ? new Department() : departmentService.getDepartmentById(id);
        model.addAttribute("department", department);

        return "department";
    }

    @PostMapping("/department")
    public String saveDepartment(Department department) {
        departmentService.saveDepartment(department);

        return "redirect:/departments";
    }
}
