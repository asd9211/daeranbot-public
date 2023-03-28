package com.project.drbot.notice.application;

import com.project.drbot.MockData;
import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.notice.infra.NoticeRepository;
import com.project.drbot.notice.presentation.dto.request.NoticeCreateDto;
import com.project.drbot.notice.presentation.dto.request.NoticeUpdateDto;
import com.project.drbot.user.application.UserService;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("공지사항 서비스 테스트")
class NoticeServiceTest {

    @InjectMocks
    public NoticeService noticeService;
    @Mock
    UserService userService;
    @Mock
    NoticeRepository noticeRepository;

    UserEntity mockUser = MockData.유저정보();

    NoticeEntity mockNotice = MockData.공지사항();

    @Nested
    class 공지사항_등록_테스트 {
        @Test
        public void 공지사항_등록_성공() {
            //given
            NoticeCreateDto request = 공지사항생성정보();
            UserEntity user = mockUser;

            doReturn(user).when(userService).findByUsername(any(String.class));

            //when
            NoticeEntity result = noticeService.addBoard(request);

            //then
            Assertions.assertEquals(request.getTitle(), result.getTitle());
        }
    }

    @Nested
    class 공지사항_수정_테스트 {

        @Test
        public void 공지사항_수정_성공() {
            //given
            NoticeUpdateDto request = 공지사항수정정보();
            NoticeEntity entity = mockNotice;

            doReturn(Optional.of(entity)).when(noticeRepository).findById(any(Long.class));

            //when
            NoticeEntity result = noticeService.modifyBoard(request);

            //then
            Assertions.assertEquals(request.getTitle(), result.getTitle());
        }

        @Test
        public void 공지사항_조회수_수정_성공() {
            //given
            NoticeEntity entity = mockNotice;
            Long viewCount = entity.getViewCount();

            doReturn(Optional.of(entity)).when(noticeRepository).findById(any(Long.class));

            //when
            NoticeEntity result = noticeService.modifyBoardViewCount(1L);

            //then
            Assertions.assertTrue(viewCount < result.getViewCount());
        }
    }

    NoticeCreateDto 공지사항생성정보() {
        return NoticeCreateDto.builder()
                .title("제목 테스트")
                .content("공지사항 내용입니다.")
                .writer(mockUser.getUsername())
                .build();
    }

    NoticeUpdateDto 공지사항수정정보() {
        return NoticeUpdateDto.builder()
                .id(1L)
                .title("수정된 제목")
                .content("수정된 내용")
                .build();
    }


}