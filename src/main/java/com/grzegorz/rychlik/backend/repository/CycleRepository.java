package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleRepository extends JpaRepository<Cycle,Long> {
    List<Cycle> findByUserId(Long id);
}
