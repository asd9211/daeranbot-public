package com.project.drbot.common.file;

import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.util.FileNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/image-upload")
public class ImageUploadRestController {

    @Value("${img.save_path}")
    private String imgSavePath;

    /**
     * 이미지를 업로드 합니다.
     *
     * @param multi  MultipartFile 정보
     * @return 파일 생성 정보
     */
    @PostMapping
    public FileCreateDto imageUpload(@RequestParam("image") MultipartFile multi) {
        FileCreateDto dto = new FileCreateDto();

        try {
            String uploadPath = imgSavePath;
            String originFilename = multi.getOriginalFilename();
            String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());

            String saveFileName = FileNameGenerator.generateFileName(extName);

            if (!multi.isEmpty()) {
                File file = new File(uploadPath, saveFileName);
                multi.transferTo(file);

                dto.setFilename(saveFileName);
                dto.setUploadPath(file.getAbsolutePath());
                dto.setUrl(uploadPath + saveFileName);

                log.info("Url : {}", (uploadPath + saveFileName));
            }
        } catch (Exception e) {
            log.info("파일생성실패 ::: {}", e.getMessage());
            throw new ServiceException("이미지 생성에 실패했습니다.");
        }
        return dto;
    }
}
