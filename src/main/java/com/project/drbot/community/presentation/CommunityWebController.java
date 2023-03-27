package com.project.drbot.community.presentation;

import com.project.drbot.community.domain.CommunityEntity;
import com.project.drbot.community.presentation.dto.response.CommunityResponse;
import com.project.drbot.community.application.CommunityService;
import com.project.drbot.user.application.UserService;
import com.project.drbot.user.presentation.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class CommunityWebController {

    private final CommunityService communityService;

    private final UserService userService;

    @ModelAttribute("userInfo")
    public UserInfoResponse userInfo() {
        return userService.getLoginUserInfo();
    }

    @GetMapping("/list/page")
    public String noticeView(Model model) {
        return "community/communityList";
    }

    @GetMapping("/regist/page")
    public String noticeRegistView(Model model) {
        return "community/communityRegist";
    }


    @GetMapping("/{communityId}")
    public String noticeInfoView(@PathVariable("communityId") Long communityId, Model model) {
        CommunityEntity entity = communityService.findById(communityId);
        model.addAttribute("communityInfo", new CommunityResponse(entity));
        return "community/communityInfo";
    }



}
