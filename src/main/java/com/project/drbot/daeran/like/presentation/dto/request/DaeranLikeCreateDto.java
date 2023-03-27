package com.project.drbot.daeran.like.presentation.dto.request;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DaeranLikeCreateDto {
    private Long daeranId;
    private String username;

    public DaeranLikeEntity toEntity(DaeranBoardEntity daeran, UserEntity user){
        return DaeranLikeEntity.builder()
                .daeran(daeran)
                .user(user)
                .build();
    }
}
