package com.fresherlancer.app.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateExperianceDTO {

    private String companyName;
    private String position;
    private LocalDate startYear;
    private LocalDate endYear;
    private String description;
}
