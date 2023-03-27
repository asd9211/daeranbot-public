package com.project.drbot.common.scheduler;


import com.project.drbot.daeran.board.application.DaeranBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteScheduler {
    private final DaeranBoardService daeranBoardService;


    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(initialDelay = 3000, fixedDelay = 3000000)
    public void deleteData(){
        daeranBoardService.removeBoardAndFile();
    }

}
