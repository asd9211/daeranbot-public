package com.project.drbot.visitor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_visitor")
public class VisitorEntity {
    @Id
    @GeneratedValue
    private Long id; // 기본키

    private String userIp;

    private String userAgent;

    private LocalDate date;

}
