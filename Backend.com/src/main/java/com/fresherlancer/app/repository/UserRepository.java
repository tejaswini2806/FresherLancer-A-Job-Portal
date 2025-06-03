package com.fresherlancer.app.repository;

import com.fresherlancer.app.domain.User;
import com.fresherlancer.app.dto.UserInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByCandidateId(Long candidateId);
}
