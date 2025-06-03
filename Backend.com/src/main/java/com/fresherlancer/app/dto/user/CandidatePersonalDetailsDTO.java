package com.fresherlancer.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidatePersonalDetailsDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Set<String> skills;
}
