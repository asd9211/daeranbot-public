package com.project.drbot.community.presentation;

import com.project.drbot.community.presentation.dto.request.CommunityCreateDto;
import com.project.drbot.community.presentation.dto.response.CommunityResponse;
import com.project.drbot.community.presentation.dto.request.CommunityUpdateDto;
import com.project.drbot.community.application.CommunityService;
import com.project.drbot.notice.presentation.dto.response.NoticeInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityRestController {

    private final CommunityService communityService;

    /**
     * 커뮤니티 게시글을 조회합니다
     *
     * @return 커뮤니티 게시글 리스트
     */
    @GetMapping
    public List<CommunityResponse> boardList() {
        return communityService.findAll().stream()
                .map(entity -> new CommunityResponse(entity))
                .collect(Collectors.toList());
    }

    /**
     * 커뮤니티 게시글을 저장합니다
     *
     * @return 저장된 커뮤니티 게시글 정보
     */
    @PostMapping
    public CommunityResponse boardAdd(CommunityCreateDto communityCreateDto) {
        return new CommunityResponse(communityService.addBoard(communityCreateDto));
    }

    /**
     * 커뮤니티 게시글을 조회수를 수정합니다.
     *
     * @return 성공여부
     * @Param 게시글 식별자 Id
     */
    @PutMapping("/viewCount")
    public CommunityResponse viewCountAdd(Long id) {
        return new CommunityResponse(communityService.modifyBoardViewCount(id));
    }

    /**
     * 커뮤니티 게시글을 수정합니다.
     *
     * @Param 게시글 식별자 Id
     * @return 수정된 커뮤니티 게시글 정보
     */
    @PutMapping
    public CommunityResponse boardModify(CommunityUpdateDto communityUpdateDto) {
        return new CommunityResponse(communityService.modifyBoard(communityUpdateDto));
    }

    /**
     * 커뮤니티 게시글을 삭제합니다.
     *
     * @Param 게시글 식별자 Id
     * @return 성공여부
     */
    @DeleteMapping
    public boolean boardRemove(Long id) {
        return communityService.removeBoard(id);
    }
}
