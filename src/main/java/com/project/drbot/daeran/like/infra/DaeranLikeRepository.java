package com.project.drbot.daeran.like.infra;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DaeranLikeRepository extends JpaRepository<DaeranLikeEntity, Long> {
    Optional<DaeranLikeEntity> findLikeByUserAndDaeranId(UserEntity user, Long daeranId);

    List<DaeranLikeEntity> findByDaeran(DaeranBoardEntity daeran);

    @Query(value = "select l.* from tb_daeran_like l inner join tb_user u on l.user_id = u.id and u.username = :username", nativeQuery = true)
    List<DaeranLikeEntity> findAllByUserJQPL(@Param("username")String username);

    Long findCountByDaeranId(Long daeranId);
}
