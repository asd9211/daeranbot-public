package com.project.drbot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret_key}")
    private String secretKey;

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    /**
     * 객체 초기화시 Secret Key를 Base64로 인코딩합니다.
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Jwt 토큰을 생성합니다.
     *
     * @param userPk 유저의 고유식별 값
     * @param roles  유저의 권한 리스트
     * @return access token 값
     */
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    /**
     * Jwt 토큰으로 유저의 정보를 조회합니다.
     *
     * @param token Jwt 토큰
     * @return 유저 인증정보
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Jwt 토큰으로 유저의 고유식별 값을 추출합니다.
     *
     * @param token Jwt 토큰
     * @return 유저의 고유식별 값
     */
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Http요청으로 부터 token의 값을 추출합니다.
     *
     * @param request Http 요청정보
     * @return Jwt 토큰
     */
    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";

        if (cookies == null || cookies.length == 0) return token;

        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("DRBOT_AUTH_TOKEN")) {
                token = cookies[i].getValue();
            }
        }
        return token;
    }

    /**
     * 토큰의 만료여부를 확인합니다.
     *
     * @param token Jwt 토큰
     * @return 만료여부
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
