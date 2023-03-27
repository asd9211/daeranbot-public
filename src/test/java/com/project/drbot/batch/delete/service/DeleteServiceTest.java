package com.project.drbot.batch.delete.service;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class DeleteServiceTest {

    @Autowired
    DaeranBoardRepository daeranBoardRepository;

    @Test
    public void deleteDaeranData() { // 일주일 경과한 데이터 및 image파일 제거
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now().minusDays(5), LocalTime.of(23, 59, 59));
        List<Long> ids = new ArrayList<>();
        List<DaeranBoardEntity> daeranList = daeranBoardRepository.findByRegDateBetween(startDatetime, endDatetime);
        System.out.println(daeranList.size());
        for (int i = 0; i < daeranList.size(); i++) {
            DaeranBoardEntity row = daeranList.get(i);
            String savePath = row.getSavePath();
            if (savePath != null && !savePath.equals("") && !savePath.equals("C:/imgstorage/")) {
                savePath = savePath.replace("C:/imgstorage/", "/home/ec2-user/imgstorage/");

                System.out.println(savePath);
                Assertions.assertTrue(daeranList.size() > 0);

            }
        }
    }

}
