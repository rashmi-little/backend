package com.mindfire.backend.service.impl;

import com.mindfire.backend.constants.ValidatorConstants;
import com.mindfire.backend.customException.UserNotFoundException;
import com.mindfire.backend.dto.request.UserRequestDto;
import com.mindfire.backend.dto.response.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ValidatorConstants.USER_ID_NOT_FOUND));

        BeanUtils.copyProperties(userRequestDto, user);

        userRepository.save(user);
        return MapHelper.mapToUserResponse(user);
    }

    @Override
    public boolean delete(long id) {
        if (userRepository.findById(id).isEmpty()) {
            return false;
        }

        userRepository.deleteById(id);

        return true;
    }

    @Override
    public UserResponseDto getById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ValidatorConstants.USER_ID_NOT_FOUND));

        return MapHelper.mapToUserResponse(user);
    }

    @Override
    public List<UserResponseDto> getAll() {

        return userRepository.findAll()
                .stream()
                .map(MapHelper::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<UserResponseDto> getPaginatedUser(int pageNumber, int pageSize) {
        if (pageNumber <= 0) {
            throw new RuntimeException(ValidatorConstants.INVALID_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<User> pageData = userRepository.findAll(pageable);

        List<UserResponseDto> userResponse = pageData.getContent().stream().map(MapHelper::mapToUserResponse).toList();

        return new PageResponse<UserResponseDto>(userResponse,
                pageData.getTotalPages(),
                pageData.getTotalElements(),
                pageData.getSize(),
                pageData.getNumber());
    }
}
