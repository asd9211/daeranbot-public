package com.project.drbot.notice;

import com.project.drbot.notice.application.NoticeService;
import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.notice.presentation.dto.request.NoticeCreateDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class NoticeServiceTest {

    @Autowired
    public NoticeService noticeService;

    @Test
    public void write_board(){
        //given
        NoticeCreateDto request = NoticeCreateDto.builder()
                .title("제목 테스트")
                .content("공지사항 내용입니다.")
                .build();

        //when
        NoticeEntity result = noticeService.addBoard(request);

        //then
        Assertions.assertNotNull(result.getId());
    }
}
