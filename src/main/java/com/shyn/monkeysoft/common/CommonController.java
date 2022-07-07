package com.shyn.monkeysoft.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {

    @GetMapping("login")
    public String getLoginView(@CookieValue("MS_LANGUAGE_KEY") String locale,
                               @RequestParam(value = "lang", required = false) String lang,
                               Model model) {
        model.addAttribute("locale", lang == null ? locale : lang);
        return "login";
    }

    @GetMapping("/")
    public String getDefaultView() {
        return "redirect:users";
    }
}
