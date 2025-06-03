package com.fresherlancer.app.dto;

import com.fresherlancer.app.eumeration.UserTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserTypeEnum userType;
    private Long candidateId;
}
