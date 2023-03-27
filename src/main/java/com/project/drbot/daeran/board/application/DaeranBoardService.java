package com.project.drbot.daeran.board.application;

import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.board.presentation.dto.request.DaeranBoardCreateDto;
import com.project.drbot.util.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DaeranBoardService {

    private final DaeranBoardRepository daeranBoardRepository;

    /**
     * 대란 게시글을 조회합니다.
     *
     * @param pageRequest page 사이즈
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBoardList(Pageable pageRequest) {
        return daeranBoardRepository.findByOrderByRegDateDesc(pageRequest);
    }

    /**
     * 대란 인기 게시글을 조회합니다.
     *
     * @return 대란 인기 게시글 리스트
     */
    public List<DaeranBoardEntity> findPopularBoardList() {
        LocalDateTime startDatetime = LocalDateTime.now().minusDays(7);
        LocalDateTime endDatetime = LocalDateTime.now();
        Long minLikeCount = 1L;

        return daeranBoardRepository.findTop10ByRegDateBetweenAndLikeCountGreaterThanEqualOrderByLikeCountDesc(startDatetime, endDatetime, minLikeCount);
    }

    /**
     * 제목 조건으로 대란 게시글을 조회합니다.
     *
     * @param pageable page 정보
     * @param title    제목
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBoardListByTitle(Pageable pageable, String title) {
        return daeranBoardRepository.findByTitleContainsOrderByRegDateDesc(title, pageable);
    }

    /**
     * 카테고리 조건으로 대란 게시글을 조회합니다.
     *
     * @param pageable page 정보
     * @param category 카테고리
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBoardListByCategory(Pageable pageable, List<String> category) {
        return daeranBoardRepository.findByCategoryInOrderByRegDateDesc(pageable, category);
    }

    /**
     * 고유식별자로 대란 게시글을 조회합니다.
     *
     * @param id 식별자
     * @return 대란 게시글
     */
    public DaeranBoardEntity findById(Long id) {
        return daeranBoardRepository
                .findById(id).orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
    }

    /**
     * 사이트이름과 카테고리 조건으로 대란 게시글을 조회합니다.
     *
     * @param pageRequest page 정보
     * @param siteNames   사이트이름
     * @param category    카테고리
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBoardListBySiteNamesAndCategory(Pageable pageRequest, List<String> siteNames, List<String> category) {
        //Todo : 추후 fetch 조인으로 select 쿼리 다중으로 나가는거 방지
        return daeranBoardRepository.findBySiteNameInAndCategoryInOrderByRegDateDesc(pageRequest, siteNames, category);
    }

    /**
     * 사이트이름 조건으로 대란 게시글을 조회합니다.
     *
     * @param pageable  page 정보
     * @param siteNames 사이트이름
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBySiteNames(Pageable pageable, List<String> siteNames) {
        return daeranBoardRepository.findBySiteNameInOrderByRegDateDesc(pageable, siteNames);
    }

    /**
     * 섹션과 사이트이름 조건으로 대란 게시글을 조회합니다.
     *
     * @param pageable  page 정보
     * @param section   섹션
     * @param siteNames 사이트이름
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBySectionAndSiteNames(String section, Pageable pageable, List<String> siteNames) {
        return daeranBoardRepository.findBySectionAndSiteNameInOrderByRegDateDesc(section, pageable, siteNames);
    }

    /**
     * 섹션 조건으로 대란 게시글을 조회합니다.
     *
     * @param pageable page 정보
     * @param section  섹션
     * @return 대란 게시글 리스트
     */
    public List<DaeranBoardEntity> findBySection(String section, Pageable pageable) {
        return daeranBoardRepository.findBySectionOrderByRegDateDesc(section, pageable);
    }

    /**
     * 제목 조건으로 중복을 체크합니다.
     *
     * @param title 제목
     * @return 중복여부
     */
    public boolean checkBoardDuplication(String title) {
        return daeranBoardRepository.existsByTitle(title);
    }

    /**
     * 대란게시글을 저장합니다.
     *
     * @param request 대란 저장 정보
     * @return 생성여부
     */
    public boolean addBoard(DaeranBoardCreateDto request) {
        DaeranBoardEntity entity = ModelMapperUtils.getModelMapper().map(request, DaeranBoardEntity.class);

        if (!checkBoardDuplication(request.getTitle()))
            daeranBoardRepository.save(entity);
        return true;
    }

    /**
     * 대란게시글 조회수를 수정합니다.
     *
     * @param id 고유식별자
     * @return 수정여부
     */
    public boolean modifyBoardViewCount(Long id) {
        DaeranBoardEntity entity = daeranBoardRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
        entity.updateViewCount(entity.getViewCount() + 1);

        return entity.getId() != null;
    }

    /**
     * 대란게시글과 이미지를 삭제합니다.
     *
     */
    public void removeBoardAndFile() { // 일주일 경과한 데이터 및 image파일 제거
        LocalDateTime startDatetime = LocalDateTime.now().minusDays(30);
        LocalDateTime endDatetime = LocalDateTime.now().minusDays(5);
        List<Long> ids = new ArrayList<>();
        String windowStorage = "C:/imgstorage/";
        String linuxStorage = "/home/ec2-user/imgstorage/";

        log.info("======파일삭제 시작=======");
        List<DaeranBoardEntity> daeranList = daeranBoardRepository.findByRegDateBetween(startDatetime, endDatetime);

        for (int i = 0; i < daeranList.size(); i++) {
            DaeranBoardEntity row = daeranList.get(i);
            String savePath = row.getSavePath();
            ids.add(row.getId());
            if (!StringUtils.isEmpty(savePath) && !savePath.equals(windowStorage)) {
                savePath = savePath.replace(windowStorage, linuxStorage);
                File file = new File(savePath);

                if (file.exists()) {
                    if (file.delete()) {
                        log.info(savePath);
                        ids.add(row.getId());
                    } else {
                        log.info(savePath + " 파일삭제 실패");
                    }
                } else {
                    log.info(savePath + " 파일이 존재하지 않습니다.");
                }
            }

            if (ids.size() >= 50 || i == (daeranList.size() - 1)) {
                daeranBoardRepository.deleteByIdIn(ids);
                log.info("======파일삭제 완료=======");
                ids.clear();
            }

        }
    }

}
