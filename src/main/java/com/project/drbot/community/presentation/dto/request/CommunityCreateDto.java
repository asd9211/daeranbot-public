package com.project.drbot.community.presentation.dto.request;

import com.project.drbot.community.domain.CommunityEntity;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.util.UserInfoProvider;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class CommunityCreateDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private Long viewCount;

    @Builder
    public CommunityCreateDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.writer = UserInfoProvider.getUsername();
        this.regDate = LocalDateTime.now();
        this.viewCount = 0L;
    }

    public CommunityEntity toEntity(UserEntity user) {
        return CommunityEntity.builder().title(title)
                .writer(writer)
                .user(user)
                .content(content)
                .regDate(regDate)
                .viewCount(viewCount)
                .build();
    }
}
