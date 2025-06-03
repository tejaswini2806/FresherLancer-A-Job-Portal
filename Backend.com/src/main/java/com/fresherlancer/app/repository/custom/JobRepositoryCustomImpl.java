package com.fresherlancer.app.repository.custom;

import com.fresherlancer.app.domain.Candidate;
import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.dto.job.JobDetailsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobRepositoryCustomImpl implements JobRepositoryCustom{

    @Autowired
    protected EntityManager entityManager;

    @Override
    public List<Candidate> getMatchingCandidatesByJobId(Long jobId) {
        StringBuilder projectionBuilder = new StringBuilder(" SELECT cand.* FROM candidate_skills c ");
        StringBuilder joinBuilder = new StringBuilder();
        joinBuilder.append(" LEFT JOIN job_skills j ON REPLACE(LOWER(c.skill), ' ', '') = REPLACE(LOWER(j.skill), ' ', '') ");
        joinBuilder.append(" LEFT JOIN job jb ON jb.id = j.job_id ");
        joinBuilder.append(" LEFT JOIN candidate cand ON cand.id = c.candidate_id ");
        joinBuilder.append(" LEFT JOIN job_applications ja ON ja.candidate_id = cand.id AND ja.job_id = jb.id ");
        joinBuilder.append(" LEFT JOIN ( " +
                "    SELECT candidate_id, " +
                "           SUM(TIMESTAMPDIFF(MONTH, start_date, end_date)) AS total_months " +
                "    FROM `candidate_experiences` " +
                "    GROUP BY candidate_id " +
                ") edu ON edu.candidate_id = c.candidate_id ");

        StringBuilder whereClauseBuilder = new StringBuilder(" WHERE ");
        whereClauseBuilder.append(" jb.id =  "+jobId);
        whereClauseBuilder.append(" AND ja.id IS NULL ");
        whereClauseBuilder.append(" AND  ( " +
                "    (jb.experience_level = 'BEGINNER' AND edu.total_months < 36) " +
                "    OR (jb.experience_level = 'INTERMEDIATE' AND edu.total_months BETWEEN 12 AND 60) " +
                "    OR (jb.experience_level = 'EXPERT' AND edu.total_months > 60) " +
                "  ) ");
        StringBuilder groupByBuilder = new StringBuilder(" GROUP BY cand.id ");

        String recordQuery = projectionBuilder.append(joinBuilder).append(whereClauseBuilder).append(groupByBuilder).toString();
        Query query = entityManager.createNativeQuery(recordQuery, Candidate.class);
        return query.getResultList();
    }

    @Override
    public List<Job> getJobDetailsByCandidateId(Long candidateId) {
        StringBuilder projectionBuilder = new StringBuilder(" SELECT DISTINCT jb.* FROM job jb ");
        StringBuilder joinBuilder = new StringBuilder();
        joinBuilder.append(" LEFT JOIN job_skills js ON js.job_id = jb.id ");
        joinBuilder.append(" LEFT JOIN candidate_skills cs ON REPLACE(LOWER(cs.skill), ' ', '') = REPLACE(LOWER(js.skill), ' ', '') ");
        joinBuilder.append(" LEFT JOIN job_applications ja ON ja.job_id = jb.id AND ja.candidate_id = " + candidateId + " ");
        joinBuilder.append(" LEFT JOIN ( " +
                "    SELECT candidate_id, " +
                "           SUM(TIMESTAMPDIFF(MONTH, start_date, end_date)) AS total_months " +
                "    FROM candidate_experiences " +
                "    GROUP BY candidate_id " +
                ") exp ON exp.candidate_id = " + candidateId + " ");

        StringBuilder whereClauseBuilder = new StringBuilder(" WHERE ");
        whereClauseBuilder.append(" cs.candidate_id = " + candidateId);
        whereClauseBuilder.append(" AND ja.id IS NULL ");
        whereClauseBuilder.append(" AND ( " +
                "    (jb.experience_level = 'BEGINNER' AND exp.total_months < 36) " +
                "    OR (jb.experience_level = 'INTERMEDIATE' AND exp.total_months BETWEEN 12 AND 60) " +
                "    OR (jb.experience_level = 'EXPERT' AND exp.total_months > 60) " +
                ") ");
        StringBuilder groupByBuilder = new StringBuilder(" GROUP BY jb.id ");

        String recordQuery = projectionBuilder.append(joinBuilder).append(whereClauseBuilder).append(groupByBuilder).toString();
        Query query = entityManager.createNativeQuery(recordQuery, Job.class);
        return query.getResultList();
    }
}
