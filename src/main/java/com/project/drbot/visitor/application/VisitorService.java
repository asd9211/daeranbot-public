package com.project.drbot.visitor.application;

import com.project.drbot.visitor.infra.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public Long getTodayVisitorCount(){
        return visitorRepository.countByDate(LocalDate.now());
    }
}
