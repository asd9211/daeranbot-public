package com.project.drbot.daeran.like.infra.impl;

import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.daeran.like.infra.DaeranLikeCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.drbot.daeran.like.domain.QDaeranLikeEntity.daeranLikeEntity;

@Repository
@RequiredArgsConstructor
public class DaeranLikeCustomRepositoryImpl implements DaeranLikeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<DaeranLikeEntity> findAllInnerFetchJoinByUsername(String username) {
        return jpaQueryFactory.selectFrom(daeranLikeEntity)
                .innerJoin(daeranLikeEntity.user)
                .fetchJoin()
                .where(daeranLikeEntity.user.username.eq(username))
                .fetch();
    }
}
