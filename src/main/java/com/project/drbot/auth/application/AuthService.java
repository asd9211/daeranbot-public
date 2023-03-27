package com.project.drbot.auth.application;

import com.project.drbot.auth.presentation.dto.request.UserCreateDto;
import com.project.drbot.auth.presentation.dto.request.UserLoginDto;
import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 정보를 등록합니다.
     *
     * @param userCreateDto 등록할 회원 정보
     * @return 성공 여부
     */
    public boolean signUp(UserCreateDto userCreateDto) {
        String encryptedPassword = passwordEncoder.encode(userCreateDto.getPassword());
        UserEntity userEntity = userCreateDto.toEntity(encryptedPassword);

        if (isAlraedyExistsMember(userEntity)) {
            throw new ServiceException(ExceptionCode.USER_ALREADY_REGISTED);
        }

        userRepository.save(userEntity);

        return userEntity.getId() != null;
    }

    /**
     * 로그인을 합니다.
     *
     * @param userLoginDto 로그인할 회원 정보
     * @return 회원 상세 정보
     */
    public UserDetails login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        UserEntity user = findByUsername(username);

        if (isPasswordFaild(password, user)) {
            throw new ServiceException(ExceptionCode.LOGIN_FAIL);
        }

        return new User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

    private boolean isAlraedyExistsMember(UserEntity userEntity) {
        return userRepository.existsByUsername(userEntity.getUsername());
    }

    private UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ExceptionCode.LOGIN_FAIL));
    }

    private boolean isPasswordFaild(String loginPassword, UserEntity user) {
        return !passwordEncoder.matches(loginPassword, user.getPassword());
    }

}