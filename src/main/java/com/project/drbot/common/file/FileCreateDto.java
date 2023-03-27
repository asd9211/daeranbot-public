package com.project.drbot.common.file;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FileCreateDto {

    private String filename;
    private String uploadPath;
    private String url;

}
