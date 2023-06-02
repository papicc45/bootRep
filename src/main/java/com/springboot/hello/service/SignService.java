package com.springboot.hello.service;

import com.springboot.hello.dto.SignInResultDTO;
import com.springboot.hello.dto.SignUpResultDTO;

public interface SignService {

    SignUpResultDTO signUp(String id, String password, String name, String role);

    SignInResultDTO signIn(String id, String password) throws RuntimeException;
}
