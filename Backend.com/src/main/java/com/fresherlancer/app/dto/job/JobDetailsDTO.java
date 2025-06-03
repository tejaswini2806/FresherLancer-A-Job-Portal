package com.fresherlancer.app.dto.job;

import com.fresherlancer.app.eumeration.EmployementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailsDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String experienceLevel;
    private Double maxSalary;
    private Double minSalary;
    private Set<String> skills;
    private LocalDateTime postedAt;
    private Integer openings;

    public JobDetailsDTO(Long id, String title, String description, String location, String employmentType, String experienceLevel,
                         Double maxSalary, Double minSalary, LocalDateTime postedAt, Integer openings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.experienceLevel = experienceLevel;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.postedAt = postedAt;
        this.openings = openings;
    }
}
