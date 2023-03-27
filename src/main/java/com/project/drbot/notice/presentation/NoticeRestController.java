package com.project.drbot.notice.presentation;

import com.project.drbot.notice.presentation.dto.NoticeDto;
import com.project.drbot.notice.presentation.dto.request.NoticeCreateDto;
import com.project.drbot.notice.presentation.dto.request.NoticeUpdateDto;
import com.project.drbot.notice.application.NoticeService;
import com.project.drbot.notice.presentation.dto.response.NoticeInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeRestController {

    private final NoticeService noticeService;

    /**
     * 공지사항을 조회합니다.
     *
     * @return 공지사항 리스트
     */
    @GetMapping
    public List<NoticeInfoResponse> boardList() {
        return noticeService.findAll()
                .stream()
                .map(entity -> new NoticeInfoResponse(entity))
                .collect(Collectors.toList());
    }

    /**
     * 공지사항을 저장합니다.
     *
     * @param noticeCreateDto 공지사항 저장 정보
     * @return 저장된 공지사항 정보
     */
    @PostMapping
    public NoticeInfoResponse boardAdd(NoticeCreateDto noticeCreateDto) {
        return new NoticeInfoResponse(noticeService.addBoard(noticeCreateDto));
    }

    /**
     * 공지사항을 조회수를 수정합니다.
     *
     * @param id 공지사항 식별자
     * @return 수정된 공지사항 정보
     */
    @PutMapping("/viewCount")
    public NoticeInfoResponse viewCountAdd(Long id) {
        return new NoticeInfoResponse(noticeService.modifyBoardViewCount(id));
    }

    /**
     * 공지사항을 수정합니다.
     *
     * @param noticeUpdateDto 공지사항 수정 정보
     * @return 수장된 공지사항 정보
     */
    @PutMapping
    public NoticeInfoResponse boardModify(NoticeUpdateDto noticeUpdateDto) {
        return new NoticeInfoResponse(noticeService.modifyBoard(noticeUpdateDto));
    }

}
