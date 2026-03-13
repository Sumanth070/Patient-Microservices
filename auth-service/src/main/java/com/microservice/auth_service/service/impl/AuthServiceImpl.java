package com.microservice.auth_service.service.impl;

import com.microservice.auth_service.dto.AdminCreateRequestUser;
import com.microservice.auth_service.dto.LoginRequest;
import com.microservice.auth_service.dto.LoginResponse;
import com.microservice.auth_service.dto.RegisterRequest;
import com.microservice.auth_service.entity.User;
import com.microservice.auth_service.exception.EmailAlreayExistException;
import com.microservice.auth_service.exception.UserNameTakenException;
import com.microservice.auth_service.mapper.UserMapper;
import com.microservice.auth_service.repository.UserRepository;
import com.microservice.auth_service.service.AuthService;
import com.microservice.auth_service.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreayExistException("Email already exists cant create new user");
        }
        if(userRepository.findByUserName(registerRequest.getUserName()).isPresent()){
            throw new UserNameTakenException("User name already exists try another name");
        }

        User user = userMapper.registerRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return userRepository.save(user);


    }

    @Override
    public User registerAdminCreaateUser(AdminCreateRequestUser adminCreateRequestUser) {
        if(userRepository.findByEmail(adminCreateRequestUser.getEmail()).isPresent()){
            throw new EmailAlreayExistException("Email already exists cant create new user");
        }
        if(userRepository.findByUserName(adminCreateRequestUser.getUserName()).isPresent()){
            throw new UserNameTakenException("User name already exists try another name");
        }

        User user = userMapper.AdminCreateRequestUserToUser(adminCreateRequestUser);
        user.setPassword(passwordEncoder.encode(adminCreateRequestUser.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getUserName(),
                user.getRole().name()
        );

        return new LoginResponse(token);
    }
}
