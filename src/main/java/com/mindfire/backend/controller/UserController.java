package com.mindfire.backend.controller;

import com.mindfire.backend.dto.request.UserRequestDto;
import com.mindfire.backend.dto.response.UserResponseDto;
import com.mindfire.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-service")
public class UserController {
    private final UserService userService;

    @PostMapping("/admin/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = userService.create(userRequestDto);

        return new ResponseEntity<UserResponseDto>(response, HttpStatus.CREATED);
    }
}
