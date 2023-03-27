package com.project.drbot.community.presentation.dto.response;

import com.project.drbot.community.domain.CommunityEntity;

import java.time.LocalDateTime;

public class CommunityResponse {

    private CommunityEntity communityEntity;

    public CommunityResponse(CommunityEntity communityEntity){
        this.communityEntity = communityEntity;
    }

    public Long getId() {
        return communityEntity.getId();
    }

    public String getTitle() {
        return communityEntity.getTitle();
    }

    public String getContent() {
        return communityEntity.getContent();
    }

    public String getImgName(){
        return communityEntity.getUser().getImgName();
    }
    public String getWriter() {
        return communityEntity.getWriter();
    }


    public LocalDateTime getRegDate() {
        return communityEntity.getRegDate();
    }

    public Long getViewCount() {
        return communityEntity.getViewCount();
    }

}
