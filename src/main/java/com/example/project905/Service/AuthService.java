package com.example.project905.Service;

import com.example.project905.Controller.Auth.LoginRequest;
import com.example.project905.Controller.Auth.LoginResponse;
import com.example.project905.Dto.UserDto;
import com.example.project905.Jwt.JwtUtil;
import com.example.project905.Modle.User;
import com.example.project905.Repo.UserRepo;
import com.example.project905.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
@Service
public class AuthService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwt;

    public String signup(UserDto dto, Locale locale) {

        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("username.exists");
        }

        User user = new User(
                dto.getUsername(),
                dto.getPassword(),
                dto.getAge(),
                dto.getRole()
        );

        userService.register(user);

        return "Signup success";
    }

    public User login(LoginRequest req, Locale locale) {

        User user = repo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("login.failed"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("login.failed");
        }

        return user;
    }
}





