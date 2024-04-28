package org.anonmes.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.anonmes.messenger.dto.AuthenticationResponseDTO;
import org.anonmes.messenger.dto.LoginPasswordAuthenticationRequestDTO;
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
@Tag(name = "Authentication", description = "Auth API")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserCredentialsService userCredentialsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "Create authentication token",
            description = "Create authentication token for user with email and password sent in request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class))})
    })
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public AuthenticationResponseDTO loginWithPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with user's email and password",
            content = @Content(schema = @Schema(implementation = LoginPasswordAuthenticationRequestDTO.class)))
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

    @Operation(
            summary = "Register a new user",
            description = "Register using email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class))})
    })
    @PostMapping("/register")
    public AuthenticationResponseDTO registerWithPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User that needs to be created",
                    content = @Content(schema = @Schema(implementation = UserCreateDTO.class)))
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

    @Operation(
            summary = "Login with Google",
            description = "Create authentication token for user logging in with Google")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class))})
    })
    @GetMapping("/login/google")
    public AuthenticationResponseDTO loginWithGoogle() {
        return null;
    }
}
