package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findByCompetitionId(Long competitionId);
    @Modifying
    @Query(value = "update contest set counted = true where id = ?1",nativeQuery = true)
    void updateCountedField(Long contestId);

}
