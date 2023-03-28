package com.project.drbot.daeran.like.application;

import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.daeran.board.application.DaeranBoardService;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.like.presentation.dto.request.DaeranLikeCreateDto;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;
import com.project.drbot.daeran.like.infra.DaeranLikeRepository;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.user.application.UserService;
import com.project.drbot.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DaeranLikeService {
    private final DaeranLikeRepository daeranLikeRepository;
    private final UserService userService;
    private final DaeranBoardService daeranBoardService;

    /**
     * 좋아요를 조회합니다.
     *
     * @param username 유저명
     * @return 성공여부
     */
    public List<DaeranLikeEntity> findByUser(String username) {
        return daeranLikeRepository.findAllByUserJQPL(username);
    }

    /**
     * 좋아요를 저장합니다.
     *
     * @param daeranLikeCreateDto 좋아요 생성 정보
     * @return 성공여부
     */
    public DaeranLikeEntity addLike(DaeranLikeCreateDto daeranLikeCreateDto) {
        UserEntity user = userService.findByUsername(daeranLikeCreateDto.getUsername());
        validateAlreadyLiked(daeranLikeCreateDto, user);

        DaeranBoardEntity daeran = daeranBoardService.findById(daeranLikeCreateDto.getDaeranId());
        DaeranLikeEntity like = daeranLikeCreateDto.toEntity(daeran, user);

        daeranLikeRepository.save(like);
        daeran.updateLikeCount();

        return like;
    }

    /**
     * 이미 좋아요를 눌렀는지 확인합니다.
     *
     * @param daeranLikeCreateDto 좋아요 생성 정보
     */
    private void validateAlreadyLiked(DaeranLikeCreateDto daeranLikeCreateDto, UserEntity user) {
        boolean isAlreadyLiked = daeranLikeRepository
                .findLikeByUserAndDaeranId(user, daeranLikeCreateDto.getDaeranId())
                .isPresent();

        if (isAlreadyLiked)
            throw new ServiceException(ExceptionCode.ALREADY_LIKED);
    }

    public Long findCountByDaeranId(Long daeranid) {
        return daeranLikeRepository.findCountByDaeranId(daeranid);
    }
}
