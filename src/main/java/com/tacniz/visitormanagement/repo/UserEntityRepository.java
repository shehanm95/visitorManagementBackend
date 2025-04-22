package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    public boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
