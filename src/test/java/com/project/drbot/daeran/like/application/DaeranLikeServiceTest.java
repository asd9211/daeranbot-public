package com.project.drbot.daeran.like.application;

import com.project.drbot.MockData;
import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.common.config.property.CommonPath;
import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.daeran.like.infra.DaeranLikeRepository;
import com.project.drbot.daeran.like.presentation.dto.request.DaeranLikeCreateDto;
import com.project.drbot.user.application.UserService;
import com.project.drbot.user.domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("대란 like 서비스 테스트")
class DaeranLikeServiceTest {

    @InjectMocks
    DaeranLikeService daeranLikeService;
    @Mock
    DaeranLikeRepository daeranLikeRepository;
    @Mock
    UserService userService;
    @Mock
    DaeranBoardService daeranBoardService;

    UserEntity mockUser = MockData.유저정보();
    DaeranBoardEntity mockDaeran = MockData.대란();

    @Nested
    class 좋아요_등록_테스트 {

        DaeranLikeCreateDto 좋아요생성정보() {
            return DaeranLikeCreateDto.builder()
                    .daeranId(1L)
                    .username(mockUser.getUsername())
                    .build();
        }

        @Test
        void 좋아요_성공() {
            //given
            DaeranLikeCreateDto daeranLikeCreateDto = 좋아요생성정보();
            DaeranBoardEntity daeran = new MockData().대란();
            UserEntity user = mockUser;
            Long likeCount = daeran.getLikeCount();

            doReturn(daeran).when(daeranBoardService).findById(any(Long.class));
            doReturn(user).when(userService).findByUsername(any(String.class));
            doReturn(Optional.empty()).when(daeranLikeRepository).findLikeByUserAndDaeranId(any(UserEntity.class), any(Long.class));

            //when
            daeranLikeService.addLike(daeranLikeCreateDto);

            //then
            Assertions.assertTrue(likeCount < daeran.getLikeCount());
        }

        @Test
        void 좋아요_실패() {
            //given
            DaeranLikeCreateDto daeranLikeCreateDto = 좋아요생성정보();
            DaeranBoardEntity daeran = mockDaeran;
            UserEntity user = mockUser;
            DaeranLikeEntity like = daeranLikeCreateDto.toEntity(daeran, user);

            doReturn(Optional.of(like)).when(daeranLikeRepository).findLikeByUserAndDaeranId(any(UserEntity.class), any(Long.class));
            doReturn(user).when(userService).findByUsername(any(String.class));

            //when
            ServiceException e = assertThrows(ServiceException.class, () -> daeranLikeService.addLike(daeranLikeCreateDto));

            //then
            String expectedMessage = "이미 좋아요를 눌렀습니다.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}