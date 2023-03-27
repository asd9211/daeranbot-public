package com.project.drbot.user.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.drbot.user.domain.UserEntity;

public class UserModifyResponse {

    @JsonIgnore
    private UserEntity userEntity;

    public UserModifyResponse(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    public Long getId() {
        return userEntity.getId();
    }

    public String getUsername() {
        return userEntity.getUsername();
    }

    public String getImgName() {
        return userEntity.getImgName();
    }

    public String getEmail() {
        return userEntity.getEmail();
    }

}
