package com.fresherlancer.app.dto.applications;

import com.fresherlancer.app.eumeration.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private Long id;
    private String candidateName;
    private String jobTitle;
    private ApplicationStatusEnum statusCode;
    private String status;
    private LocalDate appliedDate;

    public ApplicationDTO(Long id ,String candidateName, String jobTitle, ApplicationStatusEnum statusCode, LocalDate appliedDate) {
        this.id = id;
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.statusCode = statusCode;
        this.status = statusCode.getLabel();
        this.appliedDate = appliedDate;
    }
}
