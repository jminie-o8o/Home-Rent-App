package com.nextsquad.house.service;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.UserResponseDto;
import com.nextsquad.house.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return UserResponseDto.from(user);
    }
}
