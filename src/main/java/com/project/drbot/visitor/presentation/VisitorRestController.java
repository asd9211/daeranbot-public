package com.project.drbot.visitor.presentation;

import com.project.drbot.visitor.application.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitor")
public class VisitorRestController {

    private final VisitorService visitorService;

    @GetMapping("/today")
    public Long todayVisitorCount(){
        return visitorService.getTodayVisitorCount();
    }

}
