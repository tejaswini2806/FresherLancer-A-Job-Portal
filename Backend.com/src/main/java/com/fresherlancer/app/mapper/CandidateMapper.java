package com.fresherlancer.app.mapper;

import com.fresherlancer.app.domain.CandidateEducation;
import com.fresherlancer.app.domain.CandidateExperiences;
import com.fresherlancer.app.dto.user.CandidateEducationDTO;
import com.fresherlancer.app.dto.user.CandidateExperianceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CandidateMapper {

    @Mapping(source = "startDate", target = "startYear")
    @Mapping(source = "endDate", target = "endYear")
    CandidateEducationDTO toCandidateEducationDTO(CandidateEducation education);
    List<CandidateEducationDTO> toCandidateEducationDTOList(Set<CandidateEducation> educations);

    @Mapping(source = "jobTitle", target = "position")
    @Mapping(source = "startDate", target = "startYear")
    @Mapping(source = "endDate", target = "endYear")
    CandidateExperianceDTO toCandidateExperianceDTO(CandidateExperiences candidateExperiences);
    List<CandidateExperianceDTO> toCandidateExperianceDTOList(Set<CandidateExperiences> candidateExperiences);

}
