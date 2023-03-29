package com.project.drbot.notice.domain;

import com.project.drbot.user.domain.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_notice")
public class NoticeEntity {

    /**
     * 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 제목
     */
    @Column
    private String title;

    /**
     * 내용
     */
    @Column(length = 4000)
    private String content;

    /**
     * 작성자명
     */
    @Column
    private String writer;

    /**
     * 작성유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UserEntity user;

    /**
     * 등록일
     */
    @Column
    private LocalDateTime regDate;

    /**
     * 조회수
     */
    @Column
    private Long viewCount;

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }
}
