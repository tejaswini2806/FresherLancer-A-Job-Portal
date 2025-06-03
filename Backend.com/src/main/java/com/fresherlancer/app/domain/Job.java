package com.fresherlancer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fresherlancer.app.eumeration.CandidateStatusEnum;
import com.fresherlancer.app.eumeration.EmployementTypeEnum;
import com.fresherlancer.app.eumeration.JobStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String description;

    private String location;

    @Column(name = "employment_type")
    @Enumerated(EnumType.STRING)
    private EmployementTypeEnum employmentType;

    private String experienceLevel;

    private Double salaryMin;

    private Double salaryMax;

    private LocalDateTime postedAt;

    private Integer openings;

    @Column(name = "job_status")
    @Enumerated(EnumType.STRING)
    private JobStatusEnum status;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<JobSkills> jobSkills = new HashSet<>();
}
