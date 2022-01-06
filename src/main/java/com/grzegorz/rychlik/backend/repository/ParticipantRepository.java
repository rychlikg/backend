package com.grzegorz.rychlik.backend.repository;

import com.grzegorz.rychlik.backend.model.dao.Competition;
import com.grzegorz.rychlik.backend.model.dao.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByContestId(Long contestId, Sort by);
    Optional<Participant> findByUserIdAndHorseIdAndContestId(Long userId, Long horseId, Long contestId);
    Optional<Participant> findByUserIdAndCompetitionId(Long userId, Long competitionId);
    List<Participant> findByUserIdAndCompetitionIdIn(Long userId, List<Long> competitionIds);
    Page<Participant> findByUserIdAndCompetitionIdIsNotNull(Long userId, Pageable pageable);
    List<Participant> findByUserIdAndContestIdIn(Long userId, List<Long> contestsIds);
    List<Participant> findByUserIdAndContestCompetitionId(Long userId, Long competitionId);
    List<Participant> findByContestFinishedIsTrueAndContestCountedIsFalse();
    Optional<Participant> findByUserIdAndCycleId(Long userId, Long cycleId);
    List<Participant> findByCycleIdOrderByPointsDesc(Long cycleId);
}
