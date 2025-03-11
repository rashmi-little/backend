package com.mindfire.backend.controller;

import com.mindfire.backend.dto.request.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.userName(),
                loginRequestDto.password());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return "Dummy jwt token";
    }
}
