package com.project.drbot.user.domain;

import com.project.drbot.user.presentation.dto.request.UserDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id; // 기본키

    private String username; // 사용자 아이디

    private String password; // 사용자 비밀번호
    
    private String imgName; // 이미지 사진

    private String email; // 사용자 이메일

    private String role; // 권한

    @CreationTimestamp
    private LocalDateTime regDate; // 회원가입한 날짜

    public UserEntity(UserDto.Request request) {
        this.username = request.getUsername().toLowerCase();
        this.password = request.getEncryptedPassword();
        this.email = request.getEmail();
        this.role = request.getRole();
        this.regDate = LocalDateTime.now();
    }

    public void updatePassword(String changedPassword, PasswordEncoder passwordEncoder){
        this.password = (changedPassword == null || changedPassword.equals("")) ? this.password : passwordEncoder.encode(changedPassword);
    }

    public void updateImgName(String imgName){
        this.imgName = imgName;
    }

}
