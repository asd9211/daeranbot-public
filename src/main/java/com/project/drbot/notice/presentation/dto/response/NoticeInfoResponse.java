package com.project.drbot.notice.presentation.dto.response;

import com.project.drbot.notice.domain.NoticeEntity;

import java.time.LocalDateTime;

public class NoticeInfoResponse {

    private NoticeEntity noticeEntity;

    public NoticeInfoResponse(NoticeEntity noticeEntity){
        this.noticeEntity = noticeEntity;
    }

    public Long getId() {
        return noticeEntity.getId();
    }

    public String getTitle() {
        return noticeEntity.getTitle();
    }

    public String getContent() {
        return noticeEntity.getContent();
    }

    public String getWriter() {
        return noticeEntity.getWriter();
    }

    public LocalDateTime getRegDate() {
        return noticeEntity.getRegDate();
    }

    public Long getViewCount() {
        return noticeEntity.getViewCount();
    }

    public String getImgName(){
        return noticeEntity.getUser().getImgName();
    }

}
