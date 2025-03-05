package com.Greetings.service;

import com.Greetings.dto.AuthUserDTO;
import com.Greetings.dto.LoginDTO;
import com.Greetings.model.AuthUser;
import com.Greetings.repository.AuthUserRepository;
import com.Greetings.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthUserRepository authUserRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder; // ✅ Injected via constructor

    public AuthenticationService(AuthUserRepository authUserRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ User Login
    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), userOptional.get().getPassword())) {
            return null; // ❌ Invalid login credentials
        }

        return jwtUtil.generateToken(userOptional.get().getEmail());
    }

    // ✅ User Registration
    public String register(AuthUserDTO authUserDTO) {
        if (authUserRepository.findByEmail(authUserDTO.getEmail()).isPresent()) {
            return "Email is already in use.";
        }

        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName());
        user.setEmail(authUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword())); // ✅ Hashing password

        authUserRepository.save(user);
        return "User registered successfully!";
    }
}
