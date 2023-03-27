package com.project.drbot.daeran.board.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DaeranBoardCreateDto {
    private Long id;
    private String title;
    private String price;
    private String category;
    private String description;
    private String link;
    private LocalDateTime regDate;
    private String section;
    private String siteName;
    private String logoPath;
    private String savePath;
    private Long viewCount;
    private Long likeCount;
}
