package com.project.drbot.daeran.reply.presentation;

import com.project.drbot.daeran.reply.application.DaeranReplyService;
import com.project.drbot.daeran.reply.presentation.dto.request.DaeranReplyCreateDto;
import com.project.drbot.daeran.reply.presentation.dto.response.DaeranReplyInfoResponse;
import com.project.drbot.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")
public class DaeranReplyRestController {

    private final DaeranReplyService daeranReplyService;

    /**
     * 댓글을 조회합니다.
     *
     * @param boardId 대란 게시글 Id
     * @return 댓글정보 리스트
     */
    @GetMapping
    public List<DaeranReplyInfoResponse> replyList(@RequestParam(value = "boardId", required = true) Long boardId) {
        return daeranReplyService.findReplyList(boardId)
                .stream()
                .map(DaeranReplyInfoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 최근 5개의 댓글을 조회합니다.
     *
     * @return 댓글정보 리스트
     */
    @GetMapping("/last-five")
    public List<DaeranReplyInfoResponse> lastReplyList() {
        return daeranReplyService.findLastReplyList()
                .stream()
                .map(DaeranReplyInfoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 댓글을 저장합니다.
     *
     * @param daeranReplyCreateDto 댓글 저장 정보
     * @return 성공여부
     */
    @PostMapping
    public boolean replyAdd(DaeranReplyCreateDto daeranReplyCreateDto) {
        daeranReplyCreateDto.setUsername(UserInfoProvider.getUsername());
        return daeranReplyService.addReply(daeranReplyCreateDto);
    }

}
