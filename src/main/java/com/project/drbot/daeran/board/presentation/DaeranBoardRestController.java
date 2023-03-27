package com.project.drbot.daeran.board.presentation;

import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.presentation.dto.request.DaeranBoardSearchDto;
import com.project.drbot.daeran.board.presentation.dto.response.DaeranBoardInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/daeran")
@RequiredArgsConstructor
public class DaeranBoardRestController {
    private final DaeranBoardService daeranBoardService;

    /**
     * 대란게시글을 조회합니다.
     *
     * @param id 고유식별자
     * @return 대란 게시글
     */
    @GetMapping("/{id}")
    public DaeranBoardInfoResponse boardListById(@PathVariable("id") Long id) {
        return new DaeranBoardInfoResponse(daeranBoardService.findById(id));
    }

    /**
     * 대란게시글을 조회합니다.
     *
     * @param sort      정렬 조건
     * @param searchDto 조회 조건
     * @return 대란 게시글 리스트
     */
    @GetMapping
    public List<DaeranBoardInfoResponse> boardList(@RequestParam(value = "sort", required = false) String sort,
                                                   DaeranBoardSearchDto searchDto) {
        if (isLikeSorted(sort)) {
            return parseResponse(daeranBoardService.findPopularBoardList());
        }

        Pageable pageRequest = PageRequest.of(searchDto.getPage(), searchDto.getSize());

        if (isTitleSearch(searchDto)) {
            return parseResponse(daeranBoardService.findBoardListByTitle(pageRequest, searchDto.getTitle()));
        } else {
            switch (searchDto.getFilterOption()) {
                case ALL:
                    return boardListByFilter(pageRequest, searchDto);
                case CATEGORY:
                    return boardListByCategory(pageRequest, searchDto);
                case SITE_NAME:
                    return boardListBySiteName(pageRequest, searchDto);
                default:
                    return parseResponse(daeranBoardService.findBoardList(pageRequest));
            }
        }
    }

    /**
     * 대란게시글 조회수를 수정합니다.
     *
     * @param id 고유 식별자
     * @return 수정여부
     */
    @PutMapping("/viewCount")
    public boolean viewCountAdd(Long id) {
        return daeranBoardService.modifyBoardViewCount(id);
    }

    private List<DaeranBoardInfoResponse> boardListByFilter(Pageable pageRequest, DaeranBoardSearchDto searchDto) {
        List<String> siteNames = List.of(searchDto.getSiteName().split(","));
        List<String> categories = List.of(searchDto.getCategory().split(","));
        return parseResponse(daeranBoardService.findBoardListBySiteNamesAndCategory(pageRequest, siteNames, categories));
    }

    private List<DaeranBoardInfoResponse> boardListByCategory(Pageable pageRequest, DaeranBoardSearchDto searchDto) {
        List<String> categories = List.of(searchDto.getCategory().split(","));
        return parseResponse(daeranBoardService.findBoardListByCategory(pageRequest, categories));
    }

    private List<DaeranBoardInfoResponse> boardListBySiteName(Pageable pageRequest, DaeranBoardSearchDto searchDto) {
        List<String> siteNames = List.of(searchDto.getSiteName().split(","));
        return parseResponse(daeranBoardService.findBySiteNames(pageRequest, siteNames));
    }

    private boolean isTitleSearch(DaeranBoardSearchDto searchDto) {
        String title = searchDto.getTitle();
        return title != null && !title.equals("");
    }

    private boolean isLikeSorted(String sort) {
        return sort != null && sort.equals("likecount");
    }

    private List<DaeranBoardInfoResponse> parseResponse(List<DaeranBoardEntity> entityList) {
        return entityList.stream()
                .map(DaeranBoardInfoResponse::new)
                .collect(Collectors.toList());
    }
}
