package com.fresherlancer.app.repository;

import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.repository.custom.JobRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom {

}
