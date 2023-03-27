package com.project.drbot.daeran.reply.infra;

import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DaeranReplyRepository extends JpaRepository<DaeranReplyEntity, Long> {
    List<DaeranReplyEntity> findByDaeranOrderByRegDateDesc(DaeranBoardEntity daeran);

    List<DaeranReplyEntity> findTop10ByOrderByRegDateDesc();
}
