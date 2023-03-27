package com.project.drbot.common.scheduler;

import com.project.drbot.visitor.domain.VisitorEntity;
import com.project.drbot.visitor.infra.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class VisitorScheduler {

    private final RedisTemplate<String, String> redisTemplate;

    private final VisitorRepository visitorRepository;

//    @Scheduled(initialDelay = 3000000, fixedDelay = 3000000)
    @Scheduled(initialDelay = 3000, fixedDelay = 300000)
    public void updateVisitorData() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!visitorRepository.existsByUserIpAndDate(userIp, date)){
                VisitorEntity visitor = VisitorEntity.builder()
                        .userAgent(userAgent)
                        .userIp(userIp)
                        .date(date)
                        .build();

                visitorRepository.save(visitor);
            }

            redisTemplate.delete(key);
        }
    }
}
