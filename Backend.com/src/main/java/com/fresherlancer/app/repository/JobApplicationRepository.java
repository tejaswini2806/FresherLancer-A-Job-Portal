package com.fresherlancer.app.repository;

import com.fresherlancer.app.domain.JobApplications;
import com.fresherlancer.app.dto.applications.ApplicationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplications, Long> {

   boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);

   @Query("SELECT new com.fresherlancer.app.dto.applications.ApplicationDTO(ja.id, CONCAT(cand.firstName,' ',cand.lastName), job.title, ja.status, ja.appliedDate)" +
           " FROM JobApplications ja " +
           " INNER JOIN ja.candidate cand " +
           " INNER JOIN ja.job job ")
   List<ApplicationDTO> getAllApplications();

   @Query("SELECT new com.fresherlancer.app.dto.applications.ApplicationDTO(ja.id, CONCAT(cand.firstName,' ',cand.lastName), job.title, ja.status, ja.appliedDate)" +
           " FROM JobApplications ja " +
           " INNER JOIN ja.candidate cand " +
           " INNER JOIN ja.job job " +
           " WHERE ja.candidate.id = :candidateId")
   List<ApplicationDTO> getCandidateApplications(@Param("candidateId") Long candidateId);
}
