package com.shyn.monkeysoft.common;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("login")
    public String getLoginView(Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale().toString());
        return "login";
    }

    @GetMapping("/")
    public String getDefaultView() {
        return "redirect:users";
    }
}
