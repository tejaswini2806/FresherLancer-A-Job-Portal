package com.fresherlancer.app.mapper;

import com.fresherlancer.app.domain.Job;
import com.fresherlancer.app.domain.User;
import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.UserInfoDTO;
import com.fresherlancer.app.dto.job.JobRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobMapper {

    @Mapping(source = "minSalary", target = "salaryMin")
    @Mapping(source = "maxSalary", target = "salaryMax")
    void jobRequestDTOToJobEntity(JobRequestDTO requestDTO, @MappingTarget Job job);
}
