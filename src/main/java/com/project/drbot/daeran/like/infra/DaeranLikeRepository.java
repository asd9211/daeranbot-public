package com.project.drbot.daeran.like.infra;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DaeranLikeRepository extends JpaRepository<DaeranLikeEntity, Long> {
    Optional<DaeranLikeEntity> findLikeByUserAndDaeranId(UserEntity user, Long daeranId);

    List<DaeranLikeEntity> findByDaeran(DaeranBoardEntity daeran);

    Long findCountByDaeranId(Long daeranId);
}
