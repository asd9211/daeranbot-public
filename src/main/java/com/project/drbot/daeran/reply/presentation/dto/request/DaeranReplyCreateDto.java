package com.project.drbot.daeran.reply.presentation.dto.request;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DaeranReplyCreateDto {
    private Long daeranId;
    private String content;
    private String username;

    public DaeranReplyEntity toEntity(DaeranBoardEntity daeran, UserEntity user) {
        return DaeranReplyEntity.builder()
                        .daeran(daeran)
                        .user(user)
                        .content(content)
                        .regDate(LocalDateTime.now())
                        .build();
    }
}
