package com.shyn.monkeysoft.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String showDepartments(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
        Page<Department> departmentsPage = departmentService.findDepartmentsPaginated(pageRequest);

        model.addAttribute("departmentsPage", departmentsPage);

        int totalPages = departmentsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "departments";
    }

    @GetMapping("/department-create")
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());

        return "department-create";
    }

    @PostMapping("/department-create")
    public String createDepartment(Department department) {
        departmentService.saveDepartment(department);

        return "redirect:/departments";
    }

    @GetMapping("/department-delete/{id}")
    public String deleteDepartment(Model model, @PathVariable("id") Long id) {
        try {
            departmentService.deleteById(id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            // TODO: handle pagination
            return showDepartments(model, Optional.empty(), Optional.empty());
        }


        return "redirect:/departments";
    }

    @GetMapping("/department-edit/{id}")
    public String editDepartmentForm(@PathVariable("id") Long id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);

        return "department-edit";
    }

    @PostMapping("/department-edit")
    public String editDepartment(Department department) {
        departmentService.saveDepartment(department);

        return "redirect:/departments";
    }
}
