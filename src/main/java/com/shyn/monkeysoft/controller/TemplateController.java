package com.shyn.monkeysoft.controller;

import com.shyn.monkeysoft.user.MonkeyUser;
import com.shyn.monkeysoft.user.MonkeyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/")
public class TemplateController {

    private final MonkeyUserService monkeyUserService;

    @Autowired
    public TemplateController(MonkeyUserService monkeyUserService) {
        this.monkeyUserService = monkeyUserService;
    }


    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("users")
    public String showUsers(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<MonkeyUser> monkeyUserPage = monkeyUserService.findMonkeysPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("monkeyUserPage", monkeyUserPage);

        int totalPages = monkeyUserPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "users";
    }

    @GetMapping("test")
    public String testPage() {
        return "test";
    }
}
