package com.fresherlancer.app.dto.job;

import com.fresherlancer.app.eumeration.EmployementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO {

    private String title;
    private String description;
    private String location;
    private EmployementTypeEnum employmentType;
    private String experienceLevel;
    private Double maxSalary;
    private Double minSalary;
    private List<String> skills;
    private LocalDateTime postedAt;
    private Integer openings;
}
