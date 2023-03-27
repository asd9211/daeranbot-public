package com.project.drbot.common.config.filter;

import com.project.drbot.common.config.exception.TokenExpireException;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.util.JwtTokenProvider;
import com.project.drbot.util.TokenRemover;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Http Header로 작성된 token을 검증합니다.
     *
     * @param request  Http 요청정보
     * @param response HTTP 응답정보
     * @param chain    Filter 체인
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            // 헤더에서 JWT 를 받아옵니다.
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

            // 유효한 토큰인지 확인합니다.
            if (!token.equals("") && token != null && jwtTokenProvider.validateToken(token)) {
                log.info("jwt token : {}", token);
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("set auth : {}", authentication);

            }
        } catch (TokenExpireException e) {
            TokenRemover.deleteToken(response);
            request.setAttribute("exception", ExceptionCode.UNKNOWN_ERROR.getCode());
        }
        chain.doFilter(request, response);
    }
}
