package com.project.drbot.daeran.board.presentation;

import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.user.application.UserService;
import com.project.drbot.user.presentation.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/daeran")
@RequiredArgsConstructor
public class DaeranBoardWebController {

    private final DaeranBoardService daeranBoardService;

    private final UserService userService;

    @ModelAttribute("userInfo")
    public UserInfoResponse userInfo() {
        return userService.getLoginUserInfo();
    }

    @GetMapping("/popular/page")
    public String daeranPopularView(Model model) {
        return "daeran/daeranPopularList";
    }

    @GetMapping("/real-time/page")
    public String daeranRealTimeView(Model model) {
        return "daeran/daeranRealTimeList";
    }

    @GetMapping("/category/page")
    public String daeranCategoryView(@RequestParam(value = "code", required = true) String code, Model model) {
        model.addAttribute("code", code);
        return "daeran/daeranCategoryList";
    }

    @GetMapping("/origin/page")
    public String daeranOriginView(@RequestParam(value = "daeranId", required = true) String daeranId, Model model) {
        DaeranBoardEntity entity = daeranBoardService.findById(Long.valueOf(daeranId));
        model.addAttribute("link", entity.getLink());
        return "daeran/daeranOrigin";
    }

    @GetMapping("/{boardId}/page")
    public String daeranInfoView(@PathVariable("boardId") Long id, Model model) {
        model.addAttribute("id", id);
        return "daeran/daeranInfo";
    }

    @GetMapping("/search/page")
    public String daeranSearchView(@RequestParam(value = "title", required = true) String title, Model model) {
        model.addAttribute("searchTitle", title);
        return "daeran/daeranSearchList";
    }
}
