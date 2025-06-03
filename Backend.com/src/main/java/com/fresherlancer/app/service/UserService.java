package com.fresherlancer.app.service;

import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.auth.AuthRequest;
import com.fresherlancer.app.dto.auth.AuthResponse;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    GenericIdDTO<Long> registerCandidate(RegistrationRequestDTO requestDTO);
    ResponseEntity<?> authorize(AuthRequest authRequest);
}
