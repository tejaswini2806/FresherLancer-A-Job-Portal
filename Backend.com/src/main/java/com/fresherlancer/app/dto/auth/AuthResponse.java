package com.fresherlancer.app.dto.auth;

import com.fresherlancer.app.dto.UserInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {

    private String authToken;
    private UserInfoDTO userInfo;
}
