package com.project.drbot.reply;

import com.project.drbot.daeran.reply.application.DaeranReplyService;
import com.project.drbot.daeran.reply.presentation.dto.request.DaeranReplyCreateDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    public DaeranReplyService daeranReplyService;

    @Test
    public void doReply(){
        //given
        DaeranReplyCreateDto DaeranReplyCreateDto = new DaeranReplyCreateDto();
        DaeranReplyCreateDto.setDaeranId(1L);
        DaeranReplyCreateDto.setUsername("admin");
        DaeranReplyCreateDto.setContent("댓글 테스트입니다.");

        //when
        boolean reply = daeranReplyService.addReply(DaeranReplyCreateDto);

        //then
        Assertions.assertEquals(true, reply);
    }
}
