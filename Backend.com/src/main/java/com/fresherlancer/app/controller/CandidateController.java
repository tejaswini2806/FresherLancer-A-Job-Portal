package com.fresherlancer.app.controller;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/search")
    public List<Candidate> getAllCandidate(){
       return this.candidateService.getAllCandidates();
    }

    @PostMapping
    public GenericIdDTO<Long> saveCandidate(@RequestBody RegistrationRequestDTO requestDTO){
        return  this.candidateService.createCandidate(requestDTO);
    }

    @PutMapping("/{candidateId}")
    public GenericIdDTO<Long> updateCandidate(@PathVariable Long candidateId, @RequestBody RegistrationRequestDTO requestDTO) {
        return  this.candidateService.updateCandidate(candidateId,requestDTO);
    }
}
