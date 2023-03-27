package com.project.drbot.daeran.board.infra;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DaeranBoardRepository extends JpaRepository<DaeranBoardEntity, Long> {

    List<DaeranBoardEntity> findBySectionAndSiteNameInOrderByRegDateDesc(String section, Pageable pageable, List<String> siteNames);

    List<DaeranBoardEntity> findBySiteNameInOrderByRegDateDesc(Pageable pageable, List<String> siteNames);

    List<DaeranBoardEntity> findBySiteNameInAndCategoryInOrderByRegDateDesc(Pageable pageable, List<String> siteNames, List<String> category);

    List<DaeranBoardEntity> findBySectionOrderByRegDateDesc(String section, Pageable pageable);

    List<DaeranBoardEntity> findByOrderByRegDateDesc(Pageable pageable);

    List<DaeranBoardEntity> findTop10ByRegDateBetweenAndLikeCountGreaterThanEqualOrderByLikeCountDesc(LocalDateTime start, LocalDateTime end, Long likeCount);

    List<DaeranBoardEntity> findByRegDateBetween(LocalDateTime start, LocalDateTime end);

    List<DaeranBoardEntity> findByTitleContainsOrderByRegDateDesc(String title, Pageable pageable);

    List<DaeranBoardEntity> findByCategoryInOrderByRegDateDesc(Pageable pageable, List<String> category);

    Optional<DaeranBoardEntity> findById(Long id);

    boolean existsByTitle(String title);

    @Transactional
    long deleteByIdIn(List<Long> ids);
}
