package com.project.drbot.common.scheduler;

import com.project.drbot.common.crawling.WebCrawler;
import com.project.drbot.send.ImgSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SaveScheduler {

//    private final WebCrawler webCrawler;
//
//    private final ImgSender imgSender;
//
//    @Scheduled(initialDelay = 3000, fixedDelay = 3000000)
//    public void savePpomppu(){
//        webCrawler.startPpomppuCrawling();
//    }
//
//    @Scheduled(initialDelay = 3000, fixedDelay = 3000000)
//    public void saveRuliweb(){
//        webCrawler.startRuliwebCrawling();
//    }
//
//    @Scheduled(initialDelay = 600000, fixedDelay = 3000000)
//    public void saveNaverData(){
//        webCrawler.startNaverDaeranCrawling();
//        webCrawler.startNaverHotDealCrawling();
//    }
//
//    @Scheduled(initialDelay = 3000, fixedDelay = 3000000)
//    public void saveCoolenjoy(){
//        webCrawler.startCoolEnjoyCrawling();
//    }
//
//    @Scheduled(fixedDelay = 30000)
//    public void sendImg() {
//        imgSender.sendImgBySFTP();
//    }
}
