package com.fresherlancer.app.service;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.domain.JobApplications;
import com.fresherlancer.app.dto.applications.ApplicationDTO;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.eumeration.ApplicationStatusEnum;
import com.fresherlancer.app.eumeration.CandidateStatusEnum;
import com.fresherlancer.app.eumeration.JobStatusEnum;
import com.fresherlancer.app.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Transactional
    public List<ApplicationDTO> getAllApplications(){
        return this.jobApplicationRepository.getAllApplications();
    }

    @Transactional
    public List<ApplicationDTO> getCandidateApplications(Long candidateId){
        return this.jobApplicationRepository.getCandidateApplications(candidateId);
    }

    @Transactional
    public GenericIdDTO<Long> rejectApplications(Long applicationId){
        JobApplications jobApplications = jobApplicationRepository.findById(applicationId).orElseThrow(()->{
            throw new RuntimeException("Application Not Found");
        });

        Job job = jobApplications.getJob();
        job.setStatus(JobStatusEnum.REOPEN);

        Candidate candidate = jobApplications.getCandidate();
        candidate.setStatus(CandidateStatusEnum.AVAILABLE);

        jobApplications.setJob(job);
        jobApplications.setCandidate(candidate);
        jobApplications.setStatus(ApplicationStatusEnum.REJECTED);
        jobApplications = jobApplicationRepository.save(jobApplications);
        return new GenericIdDTO<>(jobApplications.getId());
    }

    @Transactional
    public GenericIdDTO<Long> approveApplications(Long applicationId){

        JobApplications jobApplications = jobApplicationRepository.findById(applicationId).orElseThrow(()->{
            throw new RuntimeException("Application Not Found");
        });

        Job job = jobApplications.getJob();
        int opennings = job.getOpenings()-1;
        job.setOpenings(opennings);
        if(opennings <= 0){
            job.setOpenings(0);
            job.setStatus(JobStatusEnum.ACTIVE);
        }else{
            job.setStatus(JobStatusEnum.BOOKED);
        }
        Candidate candidate = jobApplications.getCandidate();
        candidate.setStatus(CandidateStatusEnum.ACTIVE);

        jobApplications.setJob(job);
        jobApplications.setCandidate(candidate);
        jobApplications.setStatus(ApplicationStatusEnum.ACTIVE);
        jobApplications = jobApplicationRepository.save(jobApplications);
        return new GenericIdDTO<>(jobApplications.getId());
    }

    @Transactional
    public GenericIdDTO<Long> shortlistApplications(Long applicationId){

        JobApplications jobApplications = jobApplicationRepository.findById(applicationId).orElseThrow(()->{
            throw new RuntimeException("Application Not Found");
        });
        jobApplications.setStatus(ApplicationStatusEnum.SHORTLIST);
        jobApplications = jobApplicationRepository.save(jobApplications);
        return new GenericIdDTO<>(jobApplications.getId());
    }

    @Transactional
    public GenericIdDTO<Long> revokeApplication(Long applicationId){

        JobApplications jobApplications = jobApplicationRepository.findById(applicationId).orElseThrow(()->{
            throw new RuntimeException("Application Not Found");
        });

        Job job = jobApplications.getJob();
        int opennings = job.getOpenings()+1;
        job.setOpenings(opennings);
        job.setStatus(JobStatusEnum.OPEN);

        Candidate candidate = jobApplications.getCandidate();
        candidate.setStatus(CandidateStatusEnum.AVAILABLE);

        jobApplications.setJob(job);
        jobApplications.setCandidate(candidate);
        jobApplicationRepository.delete(jobApplications);
        return new GenericIdDTO<>(applicationId);
    }
}
