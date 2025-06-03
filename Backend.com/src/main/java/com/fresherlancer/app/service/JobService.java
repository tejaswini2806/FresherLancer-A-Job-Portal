package com.fresherlancer.app.service;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.domain.JobApplications;
import com.fresherlancer.app.domain.JobSkills;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.dto.job.JobDetailsDTO;
import com.fresherlancer.app.dto.job.JobRequestDTO;
import com.fresherlancer.app.eumeration.ApplicationStatusEnum;
import com.fresherlancer.app.eumeration.CandidateStatusEnum;
import com.fresherlancer.app.eumeration.JobStatusEnum;
import com.fresherlancer.app.mapper.JobMapper;
import com.fresherlancer.app.repository.CandidateRepository;
import com.fresherlancer.app.repository.JobApplicationRepository;
import com.fresherlancer.app.repository.JobRepository;
import com.fresherlancer.app.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository,
                      CandidateRepository candidateRepository, JobApplicationRepository jobApplicationRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobMapper = jobMapper;
    }

    @Transactional
    public List<Job> getAllJobs(){
        return  jobRepository.findAll();
    }

    @Transactional
    public GenericIdDTO<Long> createJob(JobRequestDTO jobRequestDTO){
        Job job = new Job();
        jobMapper.jobRequestDTOToJobEntity(jobRequestDTO, job);
        job.setOpenings(1);
        job.setPostedAt(LocalDateTime.now());
        job.setStatus(JobStatusEnum.OPEN);
        populateJobSkills(jobRequestDTO, job);
        job = jobRepository.save(job);
        return new GenericIdDTO<>(job.getId());
    }

    private void populateJobSkills(JobRequestDTO jobRequestDTO, Job job){
        Set<JobSkills> jobSkills = job.getJobSkills();
        CollectionUtil.populateOneToManyCollection(jobSkills, jobRequestDTO.getSkills(),
                (JobSkills skills) -> skills.getSkill(),
                (String skill) -> {
                    JobSkills domain = new JobSkills();
                    domain.setJob(job);
                    domain.setSkill(skill);
                    return domain;
                });
    }

    @Transactional
    public GenericIdDTO<Long> updateJob(Long jobId,JobRequestDTO jobRequestDTO){
        Job job = jobRepository.findById(jobId).orElseThrow(()->{
            throw new RuntimeException("Job Not Found");
        });
        job.setTitle(jobRequestDTO.getTitle());
        job.setDescription(jobRequestDTO.getDescription());
        job = jobRepository.save(job);
        return new GenericIdDTO<>(job.getId());
    }

    @Transactional
    public List<Candidate> getMatchingCandidates(Long jobId){
        Job job = jobRepository.findById(jobId).orElseThrow(()->{
            throw new RuntimeException("Job Not Found");
        });
        List<Candidate> candidates =  jobRepository.getMatchingCandidatesByJobId(jobId);
        return candidates;
    }

    @Transactional
    public GenericIdDTO<Long> submitCandidatesToJob(Long jobId, Long candidateId){
        if(jobApplicationRepository.existsByJobIdAndCandidateId(jobId, candidateId)){
            throw new RuntimeException("This Candidate Is Booked For This Job");
        }
        Job job = jobRepository.findById(jobId).orElseThrow(()->{
            throw new RuntimeException("Job Not Found");
        });

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(()->{
            throw new RuntimeException("Candidate Not Found");
        });

        int opennings = job.getOpenings()-1;
        job.setOpenings(opennings);
        if(opennings <= 0){
            job.setOpenings(0);
            job.setStatus(JobStatusEnum.ACTIVE);
        }else{
            job.setStatus(JobStatusEnum.BOOKED);
        }
        job = jobRepository.save(job);

        candidate.setStatus(CandidateStatusEnum.ACTIVE);
        candidate = candidateRepository.save(candidate);

        JobApplications jobApplications = new JobApplications();
        jobApplications.setCandidate(candidate);
        jobApplications.setJob(job);
        jobApplications.setAppliedDate(LocalDate.now());
        jobApplications.setStatus(ApplicationStatusEnum.ACTIVE);
        jobApplications = jobApplicationRepository.save(jobApplications);
        return new GenericIdDTO<>(jobApplications.getId());
    }

    @Transactional
    public List<JobDetailsDTO> getJobDetailsById(Long candidateId){
        List<JobDetailsDTO> jobDetailsDTOS = new ArrayList<>();
        List<Job> jobList = jobRepository.getJobDetailsByCandidateId(candidateId);
        for (Job job : jobList) {
            JobDetailsDTO jobDetailsDTO = new JobDetailsDTO(job.getId(),job.getTitle(), job.getDescription(), job.getLocation(), job.getEmploymentType().getLabel(),job.getExperienceLevel(),
                    job.getSalaryMax(), job.getSalaryMin(), job.getPostedAt(),job.getOpenings());
            Set<String> skills = job.getJobSkills().stream().map(item-> item.getSkill()).collect(Collectors.toSet());
            jobDetailsDTO.setSkills(skills);
            jobDetailsDTO.setExperienceLevel(StringUtils.capitalize(job.getExperienceLevel().toLowerCase()));
            jobDetailsDTOS.add(jobDetailsDTO);
        }
        return jobDetailsDTOS;
    }


    @Transactional
    public GenericIdDTO<Long> applyToJob(Long jobId,Long candidateId){
        if(jobApplicationRepository.existsByJobIdAndCandidateId(jobId, candidateId)){
            throw new RuntimeException("This Candidate Is Applied For This Job");
        }
        Job job = jobRepository.findById(jobId).orElseThrow(()->{
            throw new RuntimeException("Job Not Found");
        });

        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(()->{
            throw new RuntimeException("Candidate Not Found");
        });

        job.setStatus(JobStatusEnum.BOOKED);
        job = jobRepository.save(job);

        candidate.setStatus(CandidateStatusEnum.BOOKED);
        candidate = candidateRepository.save(candidate);

        JobApplications jobApplications = new JobApplications();
        jobApplications.setCandidate(candidate);
        jobApplications.setJob(job);
        jobApplications.setAppliedDate(LocalDate.now());
        jobApplications.setStatus(ApplicationStatusEnum.APPLIED);
        jobApplications = jobApplicationRepository.save(jobApplications);
        return new GenericIdDTO<>(jobApplications.getId());
    }
}
