package com.project.drbot.user.application;

import com.project.drbot.common.config.exception.ServiceException;
import com.project.drbot.common.config.exception.ExceptionCode;
import com.project.drbot.user.domain.UserEntity;
import com.project.drbot.user.infra.UserRepository;
import com.project.drbot.user.presentation.dto.request.UserModifyDto;
import com.project.drbot.user.presentation.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse getLoginUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return new UserInfoResponse(userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                    () -> new ServiceException(ExceptionCode.USER_NOT_REGISTED)
            ));
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username.toLowerCase()).orElseThrow(
                () -> new ServiceException(ExceptionCode.USER_NOT_REGISTED)
        );

        return new User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

    public UserEntity modifyUserInfo(UserModifyDto userModifyDto) {
        UserEntity user = userRepository.findByUsername(userModifyDto.getUsername().toLowerCase()).orElseThrow(
                () -> new ServiceException(ExceptionCode.USER_NOT_REGISTED)
        );

        user.updateImgName(userModifyDto.getImgName());
        user.updatePassword(userModifyDto.getPassword(), passwordEncoder);

        return user;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));
    }
}
