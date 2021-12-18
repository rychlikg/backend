package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Page<User> findByRoles_Name(String roleName, Pageable pageable);
}
