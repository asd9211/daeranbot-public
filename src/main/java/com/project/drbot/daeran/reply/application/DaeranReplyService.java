package com.project.drbot.daeran.reply.application;

import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.board.infra.DaeranBoardRepository;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;
import com.project.drbot.daeran.reply.infra.DaeranReplyRepository;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.daeran.reply.presentation.dto.request.DaeranReplyCreateDto;
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
public class DaeranReplyService {

    private final DaeranReplyRepository daeranReplyRepository;
    private final DaeranBoardRepository daeranBoardRepository;
    private final UserService userService;

    /**
     * 댓글을 조회합니다.
     *
     * @param boardId 게시글 id
     * @return 댓글 리스트
     */
    public List<DaeranReplyEntity> findReplyList(Long boardId) {
        DaeranBoardEntity daeran = daeranBoardRepository.findById(boardId).orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
        return daeranReplyRepository.findByDaeranOrderByRegDateDesc(daeran);
    }

    /**
     * 최근 5개의 댓글을 조회합니다.
     *
     * @return 댓글 리스트
     */
    public List<DaeranReplyEntity> findLastReplyList() {
        return daeranReplyRepository.findTop10ByOrderByRegDateDesc();
    }

    /**
     * 댓글을 저장합니다..
     *
     * @param daeranReplyCreateDto 댓글 생성정보
     * @return 성공여부
     */
    public boolean addReply(DaeranReplyCreateDto daeranReplyCreateDto) {
        DaeranBoardEntity daeran = new DaeranBoardEntity().builder().id(daeranReplyCreateDto.getDaeranId()).build();
        UserEntity user = userService.findByUsername(daeranReplyCreateDto.getUsername());
        DaeranReplyEntity reply = daeranReplyCreateDto.toEntity(daeran, user);

        daeranReplyRepository.save(reply);
        return reply.getId() != null;
    }
}
