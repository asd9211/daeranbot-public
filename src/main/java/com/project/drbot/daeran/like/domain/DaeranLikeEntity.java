package com.project.drbot.daeran.like.domain;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_daeran_like")
public class DaeranLikeEntity implements Serializable {
    /**
     * 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * 대란 게시글
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daeran_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DaeranBoardEntity daeran;


    public Long getDaeranBoardId(){
        return daeran.getId();
    }
}
