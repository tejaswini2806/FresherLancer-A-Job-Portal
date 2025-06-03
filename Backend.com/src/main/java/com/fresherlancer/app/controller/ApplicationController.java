package com.fresherlancer.app.controller;

import com.fresherlancer.app.dto.applications.ApplicationDTO;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.service.JobApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/applications")
public class ApplicationController {

    private final JobApplicationService applicationService;

    public ApplicationController(JobApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/search")
    List<ApplicationDTO> getAllApplications(){
        return this.applicationService.getAllApplications();
    }

    @GetMapping("/candidate/{candidateId}/search")
    List<ApplicationDTO> getAllApplications(@PathVariable("candidateId") Long candidateId){
        return this.applicationService.getCandidateApplications(candidateId);
    }

    @GetMapping("/{applicationId}/reject")
    GenericIdDTO<Long> rejectApplications(@PathVariable("applicationId") Long applicationId){
        return this.applicationService.rejectApplications(applicationId);
    }

    @GetMapping("/{applicationId}/approve")
    GenericIdDTO<Long> approveApplications(@PathVariable("applicationId") Long applicationId){
        return this.applicationService.approveApplications(applicationId);
    }

    @GetMapping("/{applicationId}/shortlist")
    GenericIdDTO<Long> shortlistApplications(@PathVariable("applicationId") Long applicationId){
        return this.applicationService.shortlistApplications(applicationId);
    }

    @GetMapping("/{applicationId}/revoke")
    GenericIdDTO<Long> revokeApplications(@PathVariable("applicationId") Long applicationId){
        return this.applicationService.revokeApplication(applicationId);
    }

}
