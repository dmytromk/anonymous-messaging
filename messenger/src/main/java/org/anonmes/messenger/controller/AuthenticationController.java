package org.anonmes.messenger.controller;

import lombok.AllArgsConstructor;
import org.anonmes.messenger.dto.LoginPasswordAuthenticationRequestDTO;
import org.anonmes.messenger.dto.AuthenticationResponseDTO;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.model.UserCredentials;
import org.anonmes.messenger.service.UserCredentialsService;
import org.anonmes.messenger.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserCredentialsService userCredentialsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthenticationResponseDTO loginWithPassword(
            @RequestBody LoginPasswordAuthenticationRequestDTO authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Invalid username or password", e);
        }

        final Optional<UserCredentials> userLogin =
                userCredentialsService.loadUserByUsername(authenticationRequest.getEmail());

        if (userLogin.isEmpty()) {
            throw new Exception("User not found");
        }

        final String jwt = jwtUtil.generateToken(userLogin.get().getEmail());
        return new AuthenticationResponseDTO(jwt);
    }

    @PostMapping("/register")
    public AuthenticationResponseDTO registerWithPassword(
            @RequestBody UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setEmail(userCreateDTO.getEmail());
        user.setCreatedAt(java.time.LocalDateTime.now());

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(userCreateDTO.getEmail());
        userCredentials.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        userCredentials.setActive(true);
        userCredentials.setPassword(userCreateDTO.getPassword());
        userCredentials.setRole("USER");
        userCredentials.addUser(user);
        userCredentialsService.save(userCredentials);

        final String jwt = jwtUtil.generateToken(userCredentials.getEmail());
        return new AuthenticationResponseDTO(jwt);
    }

    @GetMapping("/login/google")
    public AuthenticationResponseDTO loginWithGoogle() {
        return null;
    }
}
