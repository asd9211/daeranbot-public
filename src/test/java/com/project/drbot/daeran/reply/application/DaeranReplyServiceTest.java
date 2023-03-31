package com.project.drbot.daeran.reply.application;

import com.project.drbot.MockData;
import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;
import com.project.drbot.daeran.reply.infra.DaeranReplyRepository;
import com.project.drbot.daeran.reply.presentation.dto.request.DaeranReplyCreateDto;
import com.project.drbot.user.application.UserService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("대란 댓글 서비스 테스트")
class DaeranReplyServiceTest {


    @InjectMocks
    DaeranReplyService daeranReplyService;

    @Mock
    DaeranReplyRepository daeranReplyRepository;
    @Mock
    DaeranBoardService daeranBoardService;
    @Mock
    UserService userService;

    DaeranBoardEntity mockDaeran = MockData.대란();

    DaeranReplyEntity mockDaeranReply = MockData.댓글();

    UserEntity mockUser = MockData.유저정보();
    

    @Nested
    class 댓글_조회_테스트 {
        @Test
        void 대란_댓글_조회_성공() {
            // given
            Long id = mockDaeran.getId();

            doReturn(mockDaeran).when(daeranBoardService).findById(any(Long.class));
            doReturn(Arrays.asList(mockDaeranReply)).when(daeranReplyRepository).findByDaeranOrderByRegDateDesc(mockDaeran);

            //when
            List<DaeranReplyEntity> list = daeranReplyService.findReplyList(id);


            //then
            boolean result = list.size() > 0;
            Assertions.assertTrue(result);
        }
    }

    @Nested
    class 댓글_등록_테스트 {

        public DaeranReplyCreateDto 댓글생성정보(){
            return DaeranReplyCreateDto.builder()
                    .daeranId(1L)
                    .username("홍길동")
                    .content("댓글테스트")
                    .build();
        }

        @Test
        void 대란_댓글_등록_성공() {
            // given
            DaeranReplyCreateDto request = 댓글생성정보();

            doReturn(mockUser).when(userService).findByUsername(any(String.class));
            doReturn(mockDaeranReply).when(daeranReplyRepository).save(any(DaeranReplyEntity.class));
            doReturn(mockDaeran).when(daeranBoardService).findById(any(Long.class));

            //when
            DaeranReplyEntity entity = daeranReplyService.addReply(request);

            //then
            Assertions.assertTrue(entity.getId() != null);
        }
    }

}