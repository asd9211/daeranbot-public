package com.project.drbot.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenRemover {

    public static void deleteToken(HttpServletResponse response){
        log.info("Token을 제거합니다.");
        Cookie cookie = new Cookie("DRBOT_AUTH_TOKEN", null); // 삭제할 쿠키에 대한 값을 null로 지정
        cookie.setMaxAge(0); // 유효시간을 0으로 설정해서 바로 만료시킨다.
        response.addCookie(cookie); // 응답에 추가해서 없어지도록 함
    }
}
