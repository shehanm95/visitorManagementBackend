package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<MyUser,Long> {
}
