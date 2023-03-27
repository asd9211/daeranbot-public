package com.project.drbot.user.presentation;

import com.project.drbot.user.application.UserService;
import com.project.drbot.user.presentation.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserWebController {

    private final UserService userService;

    @ModelAttribute("userInfo")
    public UserInfoResponse userInfo() {
        return userService.getLoginUserInfo();
    }

    @GetMapping("/profile/page")
    public String userProfileView(Model model) {
        return "user/userProfile";
    }

}
