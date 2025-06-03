package com.fresherlancer.app.dto;

import com.fresherlancer.app.eumeration.UserTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserTypeEnum userType;

}
