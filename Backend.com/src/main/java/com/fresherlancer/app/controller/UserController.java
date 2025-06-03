package com.fresherlancer.app.controller;

import com.fresherlancer.app.dto.user.CandidateDetailsDTO;
import com.fresherlancer.app.service.CandidateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final CandidateService candidateService;

    public UserController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/{candidateId}")
    public CandidateDetailsDTO getCandidateProfileDetails(@PathVariable Long candidateId){
        return candidateService.getCandidateProfileDetails(candidateId);
    }

}
