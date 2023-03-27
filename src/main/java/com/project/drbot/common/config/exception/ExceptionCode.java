package com.project.drbot.common.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    UNKNOWN_ERROR(500, "A101", "알 수 없는 오류입니다."),
    WRONG_TYPE_TOKEN(400, "A102", "잘못된 토큰입니다."),
    EXPIRED_TOKEN(400, "A103", "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN(400, "A104", "지원하지않는 토큰입니다."),
    ACCESS_DENIED(400, "A105", "접근이 거절당했습니다."),

    USER_NOT_REGISTED(400, "A106", "토큰이 만료되었거나, 등록되지 않은 ID 입니다."),
    USER_ALREADY_REGISTED(400, "A107", "이미 등록된 ID입니다."),

    LOGIN_FAIL(400, "A108", "ID나 PASSWORD가 일치하지 않습니다."),

    BOARD_NOT_FOUND(400, "B101", "해당조건과 일치하는 게시글이 없습니다."),
    ALREADY_LIKED(400, "C101", "이미 좋아요를 눌렀습니다."),

    LIKE_BOARD_NOT_FOUNT(400, "C102", "좋아요할 게시글이 삭제됐습니다."),
    USER_NOT_FOUND(400, "D101", "해당조건과 일치하는 유저정보가 없습니다.");

    private int status;
    private String code;
    private String message;

    ExceptionCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
