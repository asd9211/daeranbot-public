package com.project.drbot.main.presentation;

import com.project.drbot.user.application.UserService;
import com.project.drbot.user.presentation.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @ModelAttribute("userInfo")
    public UserInfoResponse userInfo() {
        return userService.getLoginUserInfo();
    }

    @GetMapping("/")
    public String goMain(Model model) {
        return "main/main";
    }


}
