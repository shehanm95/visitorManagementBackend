package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    public boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);




        // New search method
        List<UserEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                String firstName, String lastName, String email, Pageable pageable);
    }


