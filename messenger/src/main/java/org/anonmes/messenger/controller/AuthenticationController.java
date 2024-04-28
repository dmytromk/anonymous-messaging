package org.anonmes.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.anonmes.messenger.dto.AuthenticationRequestDTO;
import org.anonmes.messenger.dto.AuthenticationResponseDTO;
import org.anonmes.messenger.service.UserLoginService;
import org.anonmes.messenger.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentication", description = "Auth API")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserLoginService userLoginService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserLoginService userLoginService,
                                    JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userLoginService = userLoginService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Create authentication token",
            description = "Create authentication token for user with email and password sent in request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password supplied",
                    content = @Content)
    })
    @PostMapping(value = "/authenticate")
    public AuthenticationResponseDTO createAuthenticationToken(
            @RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (Exception e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userLogin =
                userLoginService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userLogin.getUsername());
        return new AuthenticationResponseDTO(jwt);
    }
}
