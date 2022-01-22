package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    List<Competition> findByUserId(Long userId);
    List<Competition> findByUserEmail(String userEmail);


    Page<Competition> findByUserEmail(String currentUserEmail, Pageable pageable);

    List<Competition> findByCycleId(Long cycleId);

    List<Competition> findTop10ByNameContains(String name);
}
