package com.fresherlancer.app.controller.unsecured;

import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.auth.AuthRequest;
import com.fresherlancer.app.dto.auth.AuthResponse;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.service.UserService;
import com.fresherlancer.app.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/unsecured/auth")
public class UnsecuredAuthController {

    private final UserService userService;

    public UnsecuredAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public GenericIdDTO<Long> registerCandidate(@RequestBody RegistrationRequestDTO requestDTO){
        return this.userService.registerCandidate(requestDTO);
    }
}
