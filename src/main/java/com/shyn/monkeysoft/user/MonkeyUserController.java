package com.shyn.monkeysoft.user;

import com.shyn.monkeysoft.department.Department;
import com.shyn.monkeysoft.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MonkeyUserController {

    private final DepartmentService departmentService;
    private final MonkeyUserService monkeyUserService;

    @Autowired
    public MonkeyUserController(DepartmentService departmentService, MonkeyUserService monkeyUserService) {
        this.departmentService = departmentService;
        this.monkeyUserService = monkeyUserService;
    }

    @GetMapping("users")
    public String showUsers() {
        return "users";
    }

    @GetMapping({"user","user/{id}"})
    public String editUser(@PathVariable(value = "id", required = false) Long id, Model model) {
        MonkeyUser monkeyUser = id == null ? new MonkeyUser() : monkeyUserService.findMonkeyById(id);
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departments", departmentList);
        model.addAttribute("monkeyUser", monkeyUser);

        return "user";
    }

    @PostMapping("user")
    public String saveUser(MonkeyUser monkeyUser) {
        if(monkeyUser.getId() == null) {
            monkeyUserService.addNewMonkeyUser(monkeyUser);
        } else {
            monkeyUserService.saveMonkeyUserChanges(monkeyUser);
        }

        return "redirect:users";
    }
}
