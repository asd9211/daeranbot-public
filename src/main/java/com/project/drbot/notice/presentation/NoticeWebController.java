package com.project.drbot.notice.presentation;

import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.notice.application.NoticeService;
import com.project.drbot.notice.presentation.dto.response.NoticeInfoResponse;
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
@RequestMapping("/notice")
public class NoticeWebController {

    private final NoticeService noticeService;

    private final UserService userService;

    @ModelAttribute("userInfo")
    public UserInfoResponse userInfo() {
        return userService.getLoginUserInfo();
    }

    @GetMapping("/list/page")
    public String noticeView(Model model) {
        return "notice/noticeList";
    }

    @GetMapping("/regist/page")
    public String noticeRegistView(Model model) {
        return "notice/noticeRegist";
    }


    @GetMapping("/{noticeId}")
    public String noticeInfoView(@PathVariable("noticeId") Long noticeId, Model model) {
        NoticeEntity entity = noticeService.findById(noticeId);
        model.addAttribute("noticeInfo", new NoticeInfoResponse(entity));
        return "notice/noticeInfo";
    }

}
