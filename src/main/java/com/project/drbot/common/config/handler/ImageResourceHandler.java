package com.project.drbot.common.config.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
public class ImageResourceHandler implements WebMvcConfigurer {

    @Value("${img.storage_path}")
    private String imgStoragePath;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                // 이미지 파일의 요청 경로
                .addResourceHandler("/images/**")
                // 이미지 파일을 불러올 로컬 저장소의 위치
                .addResourceLocations(imgStoragePath);
    }
}
