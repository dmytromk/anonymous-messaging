package org.anonmes.messenger.controller;

import org.anonmes.messenger.dto.AuthenticationRequestDTO;
import org.anonmes.messenger.dto.AuthenticationResponseDTO;
import org.anonmes.messenger.service.UserLoginService;
import org.anonmes.messenger.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
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
