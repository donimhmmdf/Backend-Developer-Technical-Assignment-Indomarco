package com.indomarco.technical.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.indomarco.technical.entity.User;
import com.indomarco.technical.model.AuthResponse;
import com.indomarco.technical.model.LoginRequest;
import com.indomarco.technical.model.RegisterRequest;
import com.indomarco.technical.repository.UserRepository;
import com.indomarco.technical.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final ValidationService validationService;

    public String register(RegisterRequest request) {
        validationService.validate(request);
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "OK";
    }

    public AuthResponse login(LoginRequest request) {
        validationService.validate(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String jwt = tokenProvider.generateToken(authentication);
        return new AuthResponse(jwt);
    }
}
