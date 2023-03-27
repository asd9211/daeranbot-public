package com.project.drbot.daeran.like.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.drbot.daeran.like.domain.DaeranLikeEntity;

public class DaeranLikeInfoResponse {

    @JsonIgnore
    private DaeranLikeEntity daeranLikeEntity;


    public DaeranLikeInfoResponse(DaeranLikeEntity daeranLikeEntity) {
        this.daeranLikeEntity = daeranLikeEntity;
    }

    public Long getId() {
        return daeranLikeEntity.getId();
    }

}
