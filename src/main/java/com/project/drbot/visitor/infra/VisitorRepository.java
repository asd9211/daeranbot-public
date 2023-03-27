package com.project.drbot.visitor.infra;

import com.project.drbot.visitor.domain.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
    boolean existsByUserIpAndDate(String userIp, LocalDate date);

    Long countByDate(LocalDate date);
}
