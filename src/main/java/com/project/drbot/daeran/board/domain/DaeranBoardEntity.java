package com.project.drbot.daeran.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_daeran_board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DaeranBoardEntity {


    /**
     * 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 제목
     */
    @Column(length = 500)
    @NotNull(message = "글 제목은 Null 일 수 없습니다.")
    private String title;

    /**
     * 가격
     */
    @Column
    private String price;

    /**
     * 카테고리
     */
    @Column
    private String category;

    /**
     * 설명
     */
    @Column
    private String description;

    /**
     * 링크
     */
    @Column(length = 2000)
    @NotNull(message = "링크는 Null 일 수 없습니다.")
    private String link;

    /**
     * 등록일
     */
    @Column
    private LocalDateTime regDate;

    /**
     * 섹션
     */
    @Column
    private String section;

    /**
     * 사이트명
     */
    @Column
    private String siteName;

    /**
     * 로고 위치
     */
    @Column
    private String logoPath;

    /**
     * 저장 위치
     */
    @Column
    private String savePath;

    /**
     * 조회수
     */
    @Column
    private Long viewCount;

    /**
     * 좋아요수
     */
    @Column
    private Long likeCount;

    /**
     * 댓글리스트
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "daeran")
    private List<DaeranReplyEntity> daeranReplyList = new ArrayList<>();

    public void updateViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public void plusLike() {
        this.likeCount++;
    }

    @PrePersist
    public void prePersist() {
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }

}
