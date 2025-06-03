package com.fresherlancer.app.mapper;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.User;
import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(requestDTO.getPassword()))")
    @Mapping(target = "username", source = "email")
    void registrationRequestDTOToUserEntity(RegistrationRequestDTO requestDTO, @MappingTarget User user);

    void registrationRequestDTOToCandidteEntity(RegistrationRequestDTO requestDTO, @MappingTarget Candidate candidate);

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mapping(target = "id", source = "id")
    @Mapping(target = "candidateId", source = "user.candidate.id")
    void UserToUserInfoDTO(User user, @MappingTarget UserInfoDTO userInfoDTO);
}
