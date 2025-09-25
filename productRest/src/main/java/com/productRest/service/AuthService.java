package com.productRest.service;


import com.productRest.dtos.LoginDto;

public interface AuthService {

    String login(LoginDto loginDto);
}
