package com.fresherlancer.app.controller;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.dto.job.JobDetailsDTO;
import com.fresherlancer.app.dto.job.JobRequestDTO;
import com.fresherlancer.app.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/search")
    public List<Job> findAllJobs(){
        List<Job> jobs = this.jobService.getAllJobs();
        return jobs;
    }

    @PostMapping("")
    GenericIdDTO<Long> createJob(@RequestBody JobRequestDTO jobRequestDTO){
        return this.jobService.createJob(jobRequestDTO);
    }

    @PutMapping("/{jobId}")
    GenericIdDTO<Long> createJob(@PathVariable Long jobId, @RequestBody JobRequestDTO jobRequestDTO){
        return this.jobService.updateJob(jobId, jobRequestDTO);
    }

    @GetMapping("/matchingcandidates/{jobId}")
    List<Candidate> matchingCandidates(@PathVariable Long jobId){
        return this.jobService.getMatchingCandidates(jobId);
    }

    @GetMapping("/{jobId}/submitCandidate/{candidateId}")
    GenericIdDTO<Long> submitCandidate(@PathVariable Long jobId,@PathVariable Long candidateId){
        return this.jobService.submitCandidatesToJob(jobId, candidateId);
    }

    @GetMapping("/jobdetails/{candidateId}")
    List<JobDetailsDTO> getJobDetailsById(@PathVariable Long candidateId){
        return this.jobService.getJobDetailsById(candidateId);
    }

    @GetMapping("/{jobId}/candidate/{candidateId}/apply")
    GenericIdDTO<Long> getJobDetailsById(@PathVariable Long jobId,@PathVariable Long candidateId){
        return this.jobService.applyToJob(jobId,candidateId);
    }
}
