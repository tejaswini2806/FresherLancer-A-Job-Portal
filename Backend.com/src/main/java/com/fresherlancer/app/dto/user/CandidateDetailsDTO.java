package com.fresherlancer.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDetailsDTO {

    private CandidatePersonalDetailsDTO personalDetails;
    private List<CandidateEducationDTO> educationDetails;
    private List<CandidateExperianceDTO> exparianceDetails;
}
