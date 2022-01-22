package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.Horse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HorseRepository extends JpaRepository<Horse,Long> {

    List<Horse> findByUserId(Long userId);

   Optional<Horse> findByIdAndUserId(Long id, Long userId);

    List<Horse> findTop10ByNameContains(String name);
}
