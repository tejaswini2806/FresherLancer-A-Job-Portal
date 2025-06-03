package com.fresherlancer.app.service;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.User;
import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.UserInfoDTO;
import com.fresherlancer.app.dto.auth.AuthRequest;
import com.fresherlancer.app.dto.auth.AuthResponse;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.eumeration.CandidateStatusEnum;
import com.fresherlancer.app.eumeration.UserTypeEnum;
import com.fresherlancer.app.mapper.UserMapper;
import com.fresherlancer.app.repository.CandidateRepository;
import com.fresherlancer.app.repository.UserRepository;
import com.fresherlancer.app.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil,
                           UserRepository userRepository, CandidateRepository candidateRepository,
                           UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public GenericIdDTO<Long> registerCandidate(RegistrationRequestDTO requestDTO) {
        Candidate candidate = new Candidate();
        userMapper.registrationRequestDTOToCandidteEntity(requestDTO, candidate);
        candidate.setStatus(CandidateStatusEnum.AVAILABLE);
        candidate = candidateRepository.save(candidate);
        User user = new User();
        userMapper.registrationRequestDTOToUserEntity(requestDTO, user);
        user.setUserType(UserTypeEnum.CANDIDATE);
        user.setCandidate(candidate);
        user = userRepository.save(user);
        return new GenericIdDTO<>(user.getId());
    }

    @Override
    @Transactional
    public ResponseEntity<?> authorize(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            AuthResponse authResponse = new AuthResponse();
            String jwt = jwtUtil.generateToken(authentication);

            authResponse.setAuthToken(jwt);
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userMapper.UserToUserInfoDTO((User) authentication.getPrincipal(), userInfoDTO);
            authResponse.setUserInfo(userInfoDTO);

            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
