package com.fresherlancer.app.service;

import com.fresherlancer.app.domain.*;
import com.fresherlancer.app.dto.RegistrationRequestDTO;
import com.fresherlancer.app.dto.global.GenericIdDTO;
import com.fresherlancer.app.dto.job.JobRequestDTO;
import com.fresherlancer.app.dto.user.CandidateDetailsDTO;
import com.fresherlancer.app.dto.user.CandidateEducationDTO;
import com.fresherlancer.app.dto.user.CandidatePersonalDetailsDTO;
import com.fresherlancer.app.eumeration.CandidateStatusEnum;
import com.fresherlancer.app.eumeration.UserTypeEnum;
import com.fresherlancer.app.mapper.CandidateMapper;
import com.fresherlancer.app.mapper.UserMapper;
import com.fresherlancer.app.repository.CandidateRepository;
import com.fresherlancer.app.repository.UserRepository;
import com.fresherlancer.app.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CandidateMapper candidateMapper;

    public CandidateService(CandidateRepository candidateRepository, UserMapper userMapper,
                            UserRepository userRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.candidateMapper = candidateMapper;
    }

    @Transactional
    public List<Candidate> getAllCandidates(){
        return  candidateRepository.findAll();
    }

    @Transactional
    public GenericIdDTO<Long> createCandidate(RegistrationRequestDTO requestDTO) {
        Candidate candidate = new Candidate();
        userMapper.registrationRequestDTOToCandidteEntity(requestDTO, candidate);
        candidate.setStatus(CandidateStatusEnum.AVAILABLE);
        candidate = candidateRepository.save(candidate);
        User user = new User();
        requestDTO.setPassword("candidate");
        userMapper.registrationRequestDTOToUserEntity(requestDTO, user);;
        user.setUserType(UserTypeEnum.CANDIDATE);

        user.setCandidate(candidate);
        user = userRepository.save(user);
        return new GenericIdDTO<>(user.getId());
    }

    @Transactional
    public GenericIdDTO<Long> updateCandidate(Long candidateId,RegistrationRequestDTO requestDTO) {
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(()->{
            throw new RuntimeException("Candidate Not Found");
        });
        User user = userRepository.findByCandidateId(candidateId).orElseThrow(()->{
            throw new RuntimeException("Candidate Not Found");
        });
        candidate.setFirstName(requestDTO.getFirstName());
        candidate.setLastName(requestDTO.getLastName());
        candidate.setEmail(requestDTO.getEmail());
        candidate = candidateRepository.save(candidate);

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());

        user.setCandidate(candidate);
        user = userRepository.save(user);
        return new GenericIdDTO<>(user.getId());
    }

    @Transactional
    public CandidateDetailsDTO getCandidateProfileDetails(Long candidateId){
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(()->{
            throw new RuntimeException("Candidate Not Found");
        });
        CandidateDetailsDTO candidateDetailsDTO = new CandidateDetailsDTO();

        CandidatePersonalDetailsDTO personalDetailsDTO = new CandidatePersonalDetailsDTO();
        personalDetailsDTO.setFirstName(candidate.getFirstName());
        personalDetailsDTO.setLastName(candidate.getLastName());
        personalDetailsDTO.setEmail(candidate.getEmail());
        Set<CandidateSkills> candidateSkills = candidate.getCandidateSkills();
        if(!CollectionUtils.isEmpty(candidateSkills))
            personalDetailsDTO.setSkills(candidateSkills.stream().map(skill-> skill.getSkill()).collect(Collectors.toSet()));

        candidateDetailsDTO.setPersonalDetails(personalDetailsDTO);
        Set<CandidateEducation> candidateEducations = candidate.getCandidateEducations();
        if(!CollectionUtils.isEmpty(candidateEducations))
            candidateDetailsDTO.setEducationDetails(candidateMapper.toCandidateEducationDTOList(candidateEducations));

        Set<CandidateExperiences> candidateExperiences = candidate.getCandidateExperiences();
        if(!CollectionUtils.isEmpty(candidateExperiences))
            candidateDetailsDTO.setExparianceDetails(candidateMapper.toCandidateExperianceDTOList(candidateExperiences));



        return candidateDetailsDTO;
    }

    @Transactional
    public GenericIdDTO<Long> updateCandidateProfileDetails(Long candidateId,CandidateDetailsDTO candidateDetailsDTO){
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(()->{
            throw new RuntimeException("Candidate Not Found");
        });
        CandidatePersonalDetailsDTO personalDetailsDTO = candidateDetailsDTO.getPersonalDetails();
        candidate.setFirstName(personalDetailsDTO.getFirstName());
        candidate.setLastName(personalDetailsDTO.getLastName());
        populateCandidateSkills(candidate, personalDetailsDTO.getSkills());
        return null;
    }

    private void populateCandidateSkills(Candidate candidate, Set<String> skills){
        Set<CandidateSkills> candidateSkills = candidate.getCandidateSkills();
        CollectionUtil.populateOneToManyCollection(candidateSkills, skills,
                (CandidateSkills candSkills) -> candSkills.getSkill(),
                (String skill) -> {
                    CandidateSkills domain = new CandidateSkills();
                    domain.setCandidate(candidate);
                    domain.setSkill(skill);
                    return domain;
                });
    }

//    private void populateCandidateSkills(Candidate candidate, Set<CandidateEducationDTO> candidateEducationDTOS){
//
//        Set<CandidateEducation> candidateEducations = candidate.getCandidateEducations();
//        CollectionUtil.populateOneToManyCollection(candidateEducations, candidateEducationDTOS,
//                (CandidateEducation edu) -> edu,
//                (CandidateEducationDTO dto) -> {
//                    CandidateEducation domain = new CandidateEducation();
//                    domain.setCandidate(candidate);
//                    domain.setDegree(dto);
//                    return domain;
//                });
//    }


//    private void populateAutoAssignOfferings(Candidate candidate, List<CandidateEducationDTO> candidateEducationDTOS) {
//
//        Map<String, CandidateEducationDTO> candidateEducationDTOMap =
//                candidateEducationDTOS.stream().collect(Collectors.toMap(obj -> obj.get, Function.identity()));
//        Set<CandidateEducation> candidateEducations = candidate.getCandidateEducations();
//
//        CollectionUtil.populateOneToManyCollectionForObjects(candidateEducations, candidateEducationDTOS,
//                (CandidateEducation candidateEducation) -> candidateEducationDTOMap.get(buildAutoAssignOfferingKey(
//                        autoAssignOffering.getOffering().getId(), autoAssignOffering.getSubOffering() != null ? autoAssignOffering.getSubOffering().getId() : null)),
//                (AutoAssignOffering autoAssignOffering, ClientOfferingSubOfferingDTO clientOfferingSubOfferingDTO) -> populateAutoAssignOfferingDetails(autoAssignOffering, clientOfferingSubOfferingDTO),
//                (ClientOfferingSubOfferingDTO offeringDTO) -> {
//                    AutoAssignOffering assignOffering = new AutoAssignOffering();
//                    assignOffering.setAutoAssign(autoAssign);
//                    populateAutoAssignOfferingDetails(assignOffering, offeringDTO);
//                    return assignOffering;
//                });
//    }

//    private void populateCandidateEducations(JobRequestDTO jobRequestDTO, Job job){
//        Map<String, ClientOfferingSubOfferingDTO> clientOfferingSubOfferingDTOMap =
//                clientOfferingSubOfferingDTOS.stream().collect(Collectors.toMap(obj -> buildAutoAssignOfferingKey(obj.getOfferingId(), obj.getSubOfferingId()), Function.identity()));
//        Set<AutoAssignOffering> autoAssignOfferings = autoAssign.getOfferings();
//
//        CollectionsUtil.populateOneToManyCollectionForObjects(autoAssignOfferings, clientOfferingSubOfferingDTOS,
//                (AutoAssignOffering autoAssignOffering) -> clientOfferingSubOfferingDTOMap.get(buildAutoAssignOfferingKey(
//                        autoAssignOffering.getOffering().getId(), autoAssignOffering.getSubOffering() != null ? autoAssignOffering.getSubOffering().getId() : null)),
//                (AutoAssignOffering autoAssignOffering, ClientOfferingSubOfferingDTO clientOfferingSubOfferingDTO) -> populateAutoAssignOfferingDetails(autoAssignOffering, clientOfferingSubOfferingDTO),
//                (ClientOfferingSubOfferingDTO offeringDTO) -> {
//                    AutoAssignOffering assignOffering = new AutoAssignOffering();
//                    assignOffering.setAutoAssign(autoAssign);
//                    populateAutoAssignOfferingDetails(assignOffering, offeringDTO);
//                    return assignOffering;
//                });
//    }

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
}
