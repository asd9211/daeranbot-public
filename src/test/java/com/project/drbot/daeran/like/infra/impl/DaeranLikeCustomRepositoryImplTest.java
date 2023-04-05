package com.project.drbot.daeran.like.infra.impl;

import com.project.drbot.MockData;
import com.project.drbot.common.config.property.CommonPath;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.daeran.like.infra.DaeranLikeRepository;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DaeranLikeCustomRepositoryImplTest {

    @Autowired
    DaeranLikeCustomRepositoryImpl daeranLikeCustomRepository;

    @Autowired
    DaeranLikeRepository daeranLikeRepository;

    @Autowired
    DaeranBoardRepository daeranBoardRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void initData() {
        UserEntity user = 유저생성();
        DaeranBoardEntity daeran = 대란생성();
        좋아요생성(user, daeran);
    }

    @Test
    public void findAllInnerFetchJoinByUsername() {
        // given
        String username = "test";

        // when
        List<DaeranLikeEntity> result = daeranLikeCustomRepository.findAllInnerFetchJoinByUsername(username);

        // then
        Assertions.assertTrue(result.size() > 0);
    }

    private UserEntity 유저생성() {
        UserEntity user = 유저정보();
        userRepository.save(user);
        return user;
    }

    private DaeranBoardEntity 대란생성() {
        DaeranBoardEntity daeran = 대란();
        daeranBoardRepository.save(daeran);
        return daeran;
    }

    private void 좋아요생성(UserEntity user, DaeranBoardEntity daeran) {
        DaeranLikeEntity like = DaeranLikeEntity.builder()
                .daeran(daeran)
                .user(user)
                .build();
        daeranLikeRepository.save(like);
    }

    private UserEntity 유저정보() {
        String username = "test";

        return UserEntity.builder()
                .id(1L)
                .username(username)
                .password("1a1a1a1a1a!@")
                .regDate(LocalDateTime.now().minusDays(1))
                .role("USER")
                .build();
    }


    private DaeranBoardEntity 대란() {
        return DaeranBoardEntity.builder()
                .title("테스트")
                .price("1000")
                .category("1")
                .description("test")
                .link("")
                .regDate(LocalDateTime.now())
                .section("SITE")
                .siteName("PPOMPPU")
                .logoPath(CommonPath.PPOMPPU_LOGO_PATH)
                .savePath("/home/imgstorage/")
                .viewCount(0L)
                .likeCount(0L)
                .build();
    }
}