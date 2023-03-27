package com.project.drbot.auth.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthWebController {
    @GetMapping("/login/page")
    public String loginView() {
        return "auth/login";
    }

    @GetMapping("/signup/page")
    public String joinView() {
        return "auth/signup";
    }
}
