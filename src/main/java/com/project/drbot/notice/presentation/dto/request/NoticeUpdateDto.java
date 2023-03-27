package com.project.drbot.notice.presentation.dto.request;

import com.project.drbot.community.domain.CommunityEntity;
import com.project.drbot.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NoticeUpdateDto {
    private Long id;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @Builder
    public NoticeUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CommunityEntity toEntity(UserEntity user) {
        return CommunityEntity.builder().title(title)
                .user(user)
                .content(content)
                .build();
    }
}
