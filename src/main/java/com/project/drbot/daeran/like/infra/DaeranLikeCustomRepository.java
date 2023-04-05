package com.project.drbot.daeran.like.infra;

import com.project.drbot.daeran.like.domain.DaeranLikeEntity;

import java.util.List;

public interface DaeranLikeCustomRepository {

    List<DaeranLikeEntity> findAllInnerFetchJoinByUsername(String username);
}
