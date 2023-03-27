package com.project.drbot.notice.application;

import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.notice.presentation.dto.NoticeDto;
import com.project.drbot.notice.presentation.dto.request.NoticeCreateDto;
import com.project.drbot.notice.presentation.dto.request.NoticeUpdateDto;
import com.project.drbot.notice.domain.NoticeEntity;
import com.project.drbot.notice.infra.NoticeRepository;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import com.project.drbot.util.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    /**
     * 공지사항을 조회합니다.
     *
     * @return 공지사항 리스트
     */
    public List<NoticeEntity> findAll() {
        return noticeRepository.findAllByOrderByRegDateDesc();
    }

    /**
     * 공지사항을 조회합니다.
     *
     * @param id 고유 식별자
     * @return 공지사항
     */
    public NoticeEntity findById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
    }

    /**
     * 공지사항을 저장합니다.
     *
     * @param noticeCreateDto 공지사항 생성 정보
     * @return 저장된 공지사항
     */
    public NoticeEntity addBoard(NoticeCreateDto noticeCreateDto) {
        UserEntity user = findUserEntityByWriter(noticeCreateDto);
        NoticeEntity entity = noticeCreateDto.toEntity(user);

        noticeRepository.save(entity);

        return entity;
    }

    /**
     * 공지사항을 조회수를 수정합니다.
     *
     * @param id 고유 식별자
     * @return 수정된 공지사항
     */
    public NoticeEntity modifyBoardViewCount(Long id) {
        NoticeEntity entity = noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
        entity.setViewCount(entity.getViewCount() + 1);
        noticeRepository.save(entity);

        return entity;
    }

    /**
     * 공지사항을 수정합니다.
     *
     * @param noticeUpdateDto 공지사항 수정 정보
     * @return 수정된 공지사항
     */
    public NoticeEntity modifyBoard(NoticeUpdateDto noticeUpdateDto) {
        Long id = noticeUpdateDto.getId();
        String title = noticeUpdateDto.getTitle();
        String content = noticeUpdateDto.getContent();

        NoticeEntity entity = findEntityById(id);
        entity.updateTitle(title);
        entity.updateContent(content);

        return entity;

    }

    private UserEntity findUserEntityByWriter(NoticeCreateDto noticeCreateDto) {
        return userRepository.findByUsername(noticeCreateDto.getWriter())
                .orElseThrow(() -> new ServiceException("일치하는 회원이 없습니다."));
    }

    private NoticeEntity findEntityById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionCode.BOARD_NOT_FOUND));
    }

}
