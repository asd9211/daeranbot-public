package com.project.drbot.board;

import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.domain.Section;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.board.presentation.dto.request.DaeranBoardCreateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class BoardServiceTest {

    @Autowired
    DaeranBoardRepository daeranBoardRepository;

    @Autowired
    DaeranBoardService daeranBoardService;


    @Test
    @DisplayName("대란글 조회")
     void getBoardByPaging(){
        // given
        String section = "SITE";
        List<String> siteNames = new ArrayList<>();
        siteNames.add("PPOMPPU");

        Pageable pageRequest = PageRequest.of(0, 5);

        //when
        List<DaeranBoardEntity> list = daeranBoardService.findBySectionAndSiteNames(section, pageRequest, siteNames);


        //then
        boolean result = list.size()>0;
        Assertions.assertTrue(result);

    }

    @Test
    @DisplayName("대란글 추가")
    void addBoard(){
        //given
        DaeranBoardCreateDto request = DaeranBoardCreateDto.builder()
                .title("대란 test")
                .link("test")
                .category("가전")
                .price("10000")
                .regDate(LocalDateTime.now())
                .section(Section.SITE.toString())
                .build();

        //when
        boolean result = daeranBoardService.addBoard(request);

        //then
        Assertions.assertTrue(result);

    }

    @Test
    @DisplayName("인기글 조회")
    void hotBoardList(){
        List<DaeranBoardEntity> result = daeranBoardService.findPopularBoardList();

        result.forEach(row -> {
            System.out.println(row.toString());
        });
    }

}
