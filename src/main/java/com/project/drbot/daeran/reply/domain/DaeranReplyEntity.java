package com.project.drbot.daeran.reply.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_reply")
@ToString
public class DaeranReplyEntity {
    /**
     * 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 내용
     */
    @Column(length = 500)
    private String content;

    /**
     * 등록일
     */
    @Column
    private LocalDateTime regDate;

    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daeran_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DaeranBoardEntity daeran;

    public Long getUserId() {
        return hasUser() ? this.user.getId() : null;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public String getUsername() {
        return hasUser() ? this.user.getUsername() : null;
    }

    public String getImgName() {
        return hasUser() ? this.user.getImgName() : null;
    }

    private boolean hasUser() {
        return this.user != null;
    }

    public Long getDaeranId() {
        return hasDaeran() ? this.daeran.getId() : null;
    }

    public String getDaeranLink() {
        return hasDaeran() ? this.daeran.getLink() : null;
    }

    private boolean hasDaeran() {
        return this.daeran != null;
    }
}
