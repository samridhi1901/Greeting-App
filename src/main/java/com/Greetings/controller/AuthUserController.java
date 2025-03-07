package com.Greetings.controller;


import com.Greetings.dto.AuthUserDTO;
import com.Greetings.dto.LoginDTO;
import com.Greetings.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthenticationService authenticationService;

    public AuthUserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AuthUserDTO authUserDTO) {
        String response = authenticationService.register(authUserDTO);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        String token = authenticationService.login(loginDTO);
        if (token == null) {
            return ResponseEntity.status(401).body("Invalid email or password!");
        }
        return ResponseEntity.ok().body("{\"message\": \"Login successful!\", \"token\": \"" + token + "\"}");
    }

}