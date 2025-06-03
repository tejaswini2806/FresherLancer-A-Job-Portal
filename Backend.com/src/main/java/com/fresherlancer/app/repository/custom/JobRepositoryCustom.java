package com.fresherlancer.app.repository.custom;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.dto.job.JobDetailsDTO;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface JobRepositoryCustom {


    public List<Candidate> getMatchingCandidatesByJobId(Long jobId);

    List<Job> getJobDetailsByCandidateId(Long candidateId);
}
