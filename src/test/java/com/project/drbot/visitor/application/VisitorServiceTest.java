package com.project.drbot.visitor.application;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class VisitorServiceTest {

    @Autowired
    private VisitorService visitorService;

    @Test
    public void 방문자수_조회(){
        //given

        //when
        Long dailyCount = visitorService.getTodayVisitorCount();

        //then
        System.out.println(dailyCount);
        Assert.assertEquals(1L, Optional.ofNullable(dailyCount));
    }

}