package com.project.drbot.user.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.drbot.user.domain.UserEntity;

import java.time.LocalDateTime;


public class UserInfoResponse {

    @JsonIgnore
    public UserEntity userEntity;

    public UserInfoResponse(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    public String getUsername() {
        return userEntity == null ? null : userEntity.getUsername();
    }

    public String getEmail() {
        return userEntity == null ? null : userEntity.getEmail();
    }

    public String getImgName() {
        return userEntity == null ? null : userEntity.getImgName();
    }

    public String getRole() {
        return userEntity == null ? null : userEntity.getRole();
    }

    public LocalDateTime getRegDate() {
        return userEntity == null ? null : userEntity.getRegDate();
    }


}


