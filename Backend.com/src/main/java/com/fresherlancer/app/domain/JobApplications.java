package com.fresherlancer.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fresherlancer.app.eumeration.ApplicationStatusEnum;
import com.fresherlancer.app.eumeration.CandidateStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private LocalDate appliedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatusEnum status;

}
