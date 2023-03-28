package com.project.drbot.daeran.board.application;

import com.project.drbot.MockData;
import com.project.drbot.common.config.property.CommonPath;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.domain.Section;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.board.presentation.dto.request.DaeranBoardCreateDto;
import com.project.drbot.user.domain.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("대란 서비스 테스트")
class DaeranBoardServiceTest {


    @InjectMocks
    DaeranBoardService daeranBoardService;

    @Mock
    DaeranBoardRepository daeranBoardRepository;

    DaeranBoardEntity mockDaeran = MockData.대란();


    @Nested
    class 대란_조회_테스트 {

        @Test
        void 대란_게시글_조회_성공() {
            // given
            Pageable pageRequest = PageRequest.of(0, 5);

            //when
            doReturn(Arrays.asList(mockDaeran)).when(daeranBoardRepository).findByOrderByRegDateDesc(pageRequest);
            List<DaeranBoardEntity> list = daeranBoardService.findBoardList(pageRequest);


            //then
            boolean result = list.size() > 0;
            Assertions.assertTrue(result);
        }

        @Test
        void 대란_인기글_조회_성공() {
            // given

            //when
            doReturn(Arrays.asList(mockDaeran)).when(daeranBoardRepository).findTop10ByRegDateBetweenAndLikeCountGreaterThanEqualOrderByLikeCountDesc(any(LocalDateTime.class), any(LocalDateTime.class), any(Long.class));
            List<DaeranBoardEntity> list = daeranBoardService.findPopularBoardList();


            //then
            boolean result = list.size() > 0;
            Assertions.assertTrue(result);
        }

    }

    @Nested
    class 대란_저장_테스트 {
        public DaeranBoardCreateDto 대란생성정보() {
            return DaeranBoardCreateDto.builder()
                    .id(1L)
                    .title("대란 test")
                    .link("test")
                    .category("가전")
                    .price("10000")
                    .regDate(LocalDateTime.now())
                    .section(Section.SITE.toString())
                    .build();
        }

        @Test
        void 대란_게시글_저장_성공() {
            //given
            DaeranBoardCreateDto request = 대란생성정보();

            //when
            doReturn(request.toEntity()).when(daeranBoardRepository).save(any(DaeranBoardEntity.class));
            boolean result = daeranBoardService.addBoard(request);

            //then
            Assertions.assertTrue(result);

        }

    }

    @Nested
    class 대란_수정_테스트 {
        @Test
        void 대란_게시글_조회수_수정_성공() {
            //given
            Long id = 1L;
            DaeranBoardEntity entity = mockDaeran;
            Long viewCount = entity.getViewCount();

            //when
            doReturn(Optional.of(entity)).when(daeranBoardRepository).findById(id);
            daeranBoardService.modifyBoardViewCount(id);

            //then
            Assertions.assertTrue(viewCount < entity.getViewCount());
        }

    }


}