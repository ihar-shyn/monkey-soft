package com.shyn.monkeysoft.user;

import com.shyn.monkeysoft.department.Department;
import com.shyn.monkeysoft.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
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

    @GetMapping("user-create")
    public String createUserForm(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departments", departmentList);
        model.addAttribute("monkeyUser", new MonkeyUser());
        return "user-create";
    }

    @PostMapping("user-create")
    public String createUser(MonkeyUser monkeyUser, Model model) {
        monkeyUserService.addMonkeyUserRaw(monkeyUser);
        return "redirect:/users";
    }

    @GetMapping("user-edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        MonkeyUser monkeyUser = monkeyUserService.findMonkeyById(id);
        model.addAttribute("monkeyUser", monkeyUser);

        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departments", departmentList);

        return "user-edit";
    }

    @PostMapping("user-edit")
    public String editUser(MonkeyUser monkeyUser) {
        monkeyUserService.saveMonkeyUserChanges(monkeyUser);

        return "redirect:/users";
    }
}
