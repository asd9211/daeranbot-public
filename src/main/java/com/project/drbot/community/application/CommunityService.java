package com.project.drbot.community.application;

import com.project.drbot.community.presentation.dto.request.CommunityCreateDto;
import com.project.drbot.community.presentation.dto.request.CommunityUpdateDto;
import com.project.drbot.community.domain.CommunityEntity;
import com.project.drbot.community.infra.CommunityRepository;
import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final UserRepository userRepository;

    /**
     * 커뮤니티를 모두 조회합니다
     *
     * @return 커뮤니티 게시글 리스트
     */
    public List<CommunityEntity> findAll() {
        return communityRepository.findAllByOrderByRegDateDesc();
    }

    /**
     * 커뮤니티 게시글을 조회합니다
     *
     * @param id 커뮤니티 게시글 Id
     * @return 커뮤니티 게시글 정보
     */
    public CommunityEntity findById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
    }

    /**
     * 커뮤니티 게시글을 등록합니다
     *
     * @param communityCreateDto 커뮤니티 게시글 생성 정보
     * @return 저장된 커뮤니티 게시글 정보
     */
    public CommunityEntity addBoard(CommunityCreateDto communityCreateDto) {
        UserEntity user = findUserEntityByWriter(communityCreateDto);
        CommunityEntity entity = communityCreateDto.toEntity(user);

        communityRepository.save(entity);

        return entity;
    }


    /**
     * 커뮤니티 게시글을 수정합니다
     *
     * @param communityUpdateDto 커뮤니티 게시글 수정 정보
     * @return 수정된 커뮤니티 게시글 정보
     */
    public CommunityEntity modifyBoard(CommunityUpdateDto communityUpdateDto) {
        Long id = communityUpdateDto.getId();
        String title = communityUpdateDto.getTitle();
        String content = communityUpdateDto.getContent();

        CommunityEntity entity = findEntityById(id);
        entity.updateTitle(title);
        entity.updateContent(content);

        return entity;
    }

    /**
     * 커뮤니티 게시글을 조회수를 증가시킵니다.
     *
     * @param id 커뮤니티 게시글 Id
     * @return 성공여부
     */
    public boolean modifyBoardViewCount(Long id) {
        CommunityEntity entity = findEntityById(id);
        entity.updateViewCount(entity.getViewCount() + 1);

        return entity.getId() != null;
    }

    /**
     * 커뮤니티 게시글을 삭제합니다.
     *
     * @param id 커뮤니티 게시글 Id
     * @return 성공여부
     */
    public boolean removeBoard(Long id) {
        CommunityEntity entity = findEntityById(id);
        communityRepository.delete(entity);
        return entity.getId() == null;
    }

    private UserEntity findUserEntityByWriter(CommunityCreateDto communityCreateDto) {
        return userRepository.findByUsername(communityCreateDto.getWriter())
                .orElseThrow(() -> new ServiceException("일치하는 회원이 없습니다."));

    }

    private CommunityEntity findEntityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
    }

}
