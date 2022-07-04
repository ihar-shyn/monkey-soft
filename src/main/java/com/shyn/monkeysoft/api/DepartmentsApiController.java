package com.shyn.monkeysoft.api;

import com.shyn.monkeysoft.department.Department;
import com.shyn.monkeysoft.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DepartmentsApiController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentsApiController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("departments/all")
    public ResponseEntity<Map<String, Object>> getDepartmentsList() {
        Map<String, Object> response = new HashMap<>();
        List<Department> departments = departmentService.getAllDepartments();
        response.put("departments", departments);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("departments")
    public ResponseEntity<Map<String, Object>> getDepartmentsDetailed(@RequestParam(required = false) String departmentCode,
                                                                      @RequestParam(required = false) String departmentDesc,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Department> departmentPage = departmentService.getDepartmentsBySearchParams(departmentCode, departmentDesc, paging);
        List<Department> departmentList = departmentPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("departments", departmentList);
        response.put("currentPage", departmentPage.getNumber());
        response.put("totalItems", departmentPage.getTotalElements());
        response.put("totalPages", departmentPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/departments/{id}")
    ResponseEntity<Map<String, Object>> removeDepartment(@PathVariable("id") Long departmentId) {
        try {
            departmentService.deleteById(departmentId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (IllegalStateException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
