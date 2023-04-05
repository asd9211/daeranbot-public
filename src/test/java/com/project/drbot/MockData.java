package com.project.drbot;

import com.project.drbot.common.config.property.CommonPath;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.domain.Section;
import com.project.drbot.daeran.board.presentation.dto.request.DaeranBoardCreateDto;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;
import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.user.domain.UserEntity;

import java.time.LocalDateTime;

public class MockData {

    private static String username = "test";
    private static String password = "1234";
    private static String email = "test@naver.com";


    public static UserEntity 유저정보() {
        return UserEntity.builder()
                .id(1L)
                .username(username)
                .password(password)
                .email(email)
                .regDate(LocalDateTime.now().minusDays(1))
                .role("USER")
                .build();
    }


    public static DaeranBoardEntity 대란() {
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

    public static NoticeEntity 공지사항() {
        return NoticeEntity.builder()
                .id(1L)
                .title("제목 테스트")
                .content("공지사항 내용입니다.")
                .writer(username)
                .viewCount(0L)
                .user(유저정보())
                .build();
    }

    public static DaeranReplyEntity 댓글(){
        return DaeranReplyEntity.builder()
                .id(1L)
                .regDate(LocalDateTime.now())
                .content("댓글 테스트")
                .daeran(대란())
                .user(유저정보())
                .build();
    }

}
