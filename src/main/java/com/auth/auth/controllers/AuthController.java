package com.auth.auth.controllers;

import com.auth.auth.models.User;
import com.auth.auth.storage.UserStorage;
import com.auth.auth.utility.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserStorage userStorage, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        authentication.getAuthorities().forEach(auth -> logger.info("Authority: " + auth.getAuthority()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        logger.info("token: " + token);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        User user = new User(registerRequest.getUsername(),passwordEncoder.encode(registerRequest.getPassword()), new HashSet<>());
        if(UserStorage.userExists(user) == null){
            UserStorage.addUser(user);

            return new ResponseEntity<>("User registered", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Username taken", HttpStatus.BAD_REQUEST);
    }
}

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {return username;}
    public String getPassword() {return password;}
}

class RegisterRequest {
    private String username;
    private String password;

    public String getUsername() {return username;}
    public String getPassword() {return password;}
}

class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponse(String token) {
        this.accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}