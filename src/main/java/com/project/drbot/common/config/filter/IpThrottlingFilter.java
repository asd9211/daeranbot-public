package com.project.drbot.common.config.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpThrottlingFilter extends GenericFilterBean {

    // 참고
    //https://baeji77.github.io/dev/java/Java-rate-limit-bukcet4j/
    //https://github.com/keets2012/microservice-integration/blob/master/gateway-enhanced/src/main/java/com/blueskykong/gateway/enhanced/filter/RateLimitByIpGatewayFilter.java#L46

    int capacity = 300;
    int refillTokens = 300;
    Duration refillDuration = Duration.ofMinutes(1);

    private static final Map<String, Bucket> CACHE = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        Refill refill = Refill.intervally(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String ip = httpRequest.getRemoteAddr();
        Bucket bucket = CACHE.computeIfAbsent(ip, k -> createNewBucket());

        // tryConsume returns false immediately if no tokens available with the bucket
        if (bucket.tryConsume(1)) {
            // the limit is not exceeded
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // limit is exceeded
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setContentType("text/plain");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().append("{\"message\":\"요청이 너무 많습니다. 1분후 다시 시도해주세요.\"}");
        }

    }

}