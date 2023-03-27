package com.project.drbot.daeran.like.presentation;

import com.project.drbot.daeran.like.presentation.dto.request.DaeranLikeCreateDto;
import com.project.drbot.daeran.like.application.DaeranLikeService;
import com.project.drbot.daeran.like.presentation.dto.response.DaeranLikeInfoResponse;
import com.project.drbot.util.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
// TODO: 2022-10-03 mapping /daeran/like로 변경
public class DaeranLikeRestController {

    private final DaeranLikeService daeranLikeService;

    /**
     * 좋아요를 조회합니다.
     *
     * @return 좋아요 정보 리스트
     */
    @GetMapping("/username")
    public List<DaeranLikeInfoResponse> likeList() {
        String username = UserInfoProvider.getUsername();
        return daeranLikeService.findByUser(username).stream()
                .map(DaeranLikeInfoResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 좋아요를 저장합니다.
     *
     * @param daeranLikeCreateDto 좋아요 생성 정보
     * @return 성공여부
     */
    @PostMapping
    public boolean likeAdd(DaeranLikeCreateDto daeranLikeCreateDto) {
        daeranLikeCreateDto.setUsername(UserInfoProvider.getUsername());
        return daeranLikeService.addLike(daeranLikeCreateDto);
    }

}
