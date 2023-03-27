package com.project.drbot.daeran.reply.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.drbot.daeran.reply.domain.DaeranReplyEntity;

import java.time.LocalDateTime;


public class DaeranReplyInfoResponse {

    @JsonIgnore
    private DaeranReplyEntity entity;

    public DaeranReplyInfoResponse(DaeranReplyEntity entity){
        this.entity = entity;
    }

    public Long getId() {
        return entity.getId();
    }

    public String getContent() {
        return entity.getContent();
    }

    public LocalDateTime getRegDate() {
        return entity.getRegDate();
    }

    public Long getUserId() {
        return entity.getUserId();
    }

    public String getUsername() {
        return entity.getUsername();
    }

    public String getImgName() {
        return entity.getImgName();
    }

    public Long getDaeranId() {
        return entity.getDaeranId();
    }

    public String getDaeranLink() {
        return entity.getDaeranLink();
    }

}
