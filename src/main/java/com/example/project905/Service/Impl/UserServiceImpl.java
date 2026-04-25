package com.example.project905.Service.Impl;

import com.example.project905.Modle.User;
import com.example.project905.Repo.UserRepo;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserDetailsService {

    private UserRepo repo;

    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepo repo, @Lazy PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // SIGN-UP
    public User register(User user) {

        if (!user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole().toUpperCase());
        }

        user.setPassword(encoder.encode(user.getPassword()));

        return repo.save(user);
    }

    // LOGIN (Used by Spring Security)
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }

}
