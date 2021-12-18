package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.Participant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByContestId(Long contestId, Sort by);
    Optional<Participant> findByUserIdAndHorseIdAndContestId(Long userId, Long horseId, Long contestId);
}
