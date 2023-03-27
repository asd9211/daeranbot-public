package com.project.drbot.board;

import com.project.drbot.common.config.property.CommonPath;
import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.domain.Section;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.board.presentation.dto.request.DaeranBoardCreateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
@DisplayName("대란보드 서비스")
class BoardServiceTest {

    @InjectMocks
    DaeranBoardService daeranBoardService;

    @Mock
    DaeranBoardRepository daeranBoardRepository;


    @Test
    void 대란_게시글_조회_성공() {
        // given
        Pageable pageRequest = PageRequest.of(0, 5);

        //when
        doReturn(Arrays.asList(mock_daeran())).when(daeranBoardRepository).findByOrderByRegDateDesc(pageRequest);
        List<DaeranBoardEntity> list = daeranBoardService.findBoardList(pageRequest);


        //then
        boolean result = list.size() > 0;
        Assertions.assertTrue(result);
    }

    @Test
    void 대란_게시글_저장_성공() {
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
        doReturn(request.toEntity()).when(daeranBoardRepository).save(any(DaeranBoardEntity.class));
        boolean result = daeranBoardService.addBoard(request);

        //then
        Assertions.assertTrue(result);

    }


    public DaeranBoardEntity mock_daeran() {
        return DaeranBoardEntity.builder()
                .id(1L)
                .title("테스트")
                .price("1000")
                .category("1")
                .description("test")
                .link("")
                .regDate(LocalDateTime.now())
                .section("SITE")
                .siteName("PPOMPPU")
                .logoPath(CommonPath.PPOMPPU_LOGO_PATH)
                .savePath("/home/imgstorage/")
                .viewCount(0L)
                .likeCount(0L)
                .build();
    }
}
