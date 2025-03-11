package com.mindfire.backend.service.impl;

import com.mindfire.backend.dto.request.UserRequestDto;
import com.mindfire.backend.dto.response.UserResponseDto;
import com.mindfire.backend.entity.User;
import com.mindfire.backend.enums.Role;
import com.mindfire.backend.mapper.MapHelper;
import com.mindfire.backend.repository.UserRepository;
import com.mindfire.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = MapHelper.mapToUser(userRequestDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return MapHelper.mapToUserResponse(savedUser);
    }

    @Override
    public UserResponseDto update(long id, UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public UserResponseDto getById(long id) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAll() {
        return List.of();
    }
}
