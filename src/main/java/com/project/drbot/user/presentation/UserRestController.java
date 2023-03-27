package com.project.drbot.user.presentation;

import com.project.drbot.user.application.UserService;
import com.project.drbot.user.presentation.dto.request.UserModifyDto;
import com.project.drbot.user.presentation.dto.response.UserModifyResponse;
import com.project.drbot.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PutMapping
    public UserModifyResponse userInfoModify(@RequestBody UserModifyDto userModifyDto) {
        userModifyDto.setUsername(UserInfoProvider.getUsername());
        return new UserModifyResponse(userService.modifyUserInfo(userModifyDto));
    }
}
