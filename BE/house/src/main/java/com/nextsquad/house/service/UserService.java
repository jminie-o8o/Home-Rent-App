package com.nextsquad.house.service;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.UserInfoDto;
import com.nextsquad.house.dto.UserResponseDto;
import com.nextsquad.house.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("없는 유저 입니다."));
        return UserResponseDto.from(user);
    }

    public GeneralResponseDto modifyUserInfo(Long id, UserInfoDto userInfoDto){
        User user = userRepository.findById(id).orElseThrow();
        user.modifyInfo(userInfoDto);
        return new GeneralResponseDto(200, "정보가 수정되었습니다");
    }
}
