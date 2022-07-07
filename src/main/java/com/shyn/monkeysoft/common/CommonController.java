package com.shyn.monkeysoft.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/")
    public String getDefaultView() {
        return "redirect:users";
    }

    // Page for tests
    @GetMapping("test")
    public String getTestPage() {
        return "test";
    }
}
