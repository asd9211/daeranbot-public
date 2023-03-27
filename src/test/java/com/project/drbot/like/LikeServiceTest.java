package com.project.drbot.like;

import com.project.drbot.daeran.like.presentation.dto.request.DaeranLikeCreateDto;
import com.project.drbot.daeran.like.application.DaeranLikeService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LikeServiceTest {

    @Autowired
    DaeranLikeService daeranLikeService;

    @Test
    public void dolike(){
        //given
        DaeranLikeCreateDto daeranLikeCreateDto = new DaeranLikeCreateDto();
        daeranLikeCreateDto.setDaeranId(1L);
        daeranLikeCreateDto.setUsername("admin");

        //when
        boolean like = daeranLikeService.addLike(daeranLikeCreateDto);

        //then
        Assertions.assertEquals(true, like);
    }
}
