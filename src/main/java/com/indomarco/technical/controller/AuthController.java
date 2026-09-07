package com.indomarco.technical.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indomarco.technical.model.AuthResponse;
import com.indomarco.technical.model.LoginRequest;
import com.indomarco.technical.model.RegisterRequest;
import com.indomarco.technical.model.WebResponse;
import com.indomarco.technical.service.AuthService;
import com.indomarco.technical.utility.ResponseUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseUtil responseUtil;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<String>> register(@RequestBody RegisterRequest request) {

        return responseUtil.successResponse("Success", authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse<AuthResponse>> login(@RequestBody LoginRequest request) {

        return responseUtil.successResponse("Success", authService.login(request));

    }

}
