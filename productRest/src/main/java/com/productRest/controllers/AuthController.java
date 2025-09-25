package com.productRest.controllers;

import com.productRest.dtos.AuthResponse;
import com.productRest.dtos.LoginDto;
import com.productRest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = {"/login", "/signin"})
    public AuthResponse login(@RequestBody LoginDto loginDto){
        String token =  authService.login(loginDto);
        AuthResponse response = new AuthResponse();
        response.setAccessToken(token);
        return response;
    }
}
