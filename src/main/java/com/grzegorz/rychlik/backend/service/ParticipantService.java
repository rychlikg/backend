package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.*;
import com.grzegorz.rychlik.backend.model.dto.ParticipantSocketUpdater;
import com.grzegorz.rychlik.backend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ContestService contestService;
    private final HorseService horseService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final  CompetitionService competitionService;

    @Transactional
    public void assignToContest(Long contestId, Long horseId) {
        User currentUser = userService.getCurrentUser();
        Horse horse = horseService.getUserHorseById(horseId, currentUser.getId());
        Contest contest = contestService.getById(contestId);
        Participant participant = Participant.builder()
                .user(currentUser)
                .horse(horse)
                .contest(contest)
                .build();
        // contest.getParticipants().add(participant);
        participantRepository.save(participant);

    }

    public List<Participant> getParticipantsByContestId(Long contestId) {
        return participantRepository.findByContestId(contestId, Sort.by(Sort.Order.asc("points"), Sort.Order.asc("roundTime"), Sort.Order.asc("orderNumber")));
    }

    public void saveTime(String time, Long horseId, Long userId, Long contestId) {
        participantRepository.findByUserIdAndHorseIdAndContestId(userId, horseId, contestId).ifPresent(participant -> {
            String[] split = time.split(":");
            LocalTime localTime = LocalTime.of(0, Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            participant.setRoundTime(localTime);
            participantRepository.save(participant);
            simpMessagingTemplate.convertAndSend("/topic/participants/" + contestId, new ParticipantSocketUpdater(userId, null, localTime.toString(), horseId, null));
        });
    }

    public void savePoints(int points, Long horseId, Long userId, Long contestId) {
        participantRepository.findByUserIdAndHorseIdAndContestId(userId, horseId, contestId).ifPresent(participant -> {
            participant.setPoints(points);
            participantRepository.save(participant);
            simpMessagingTemplate.convertAndSend("/topic/participants/" + contestId, new ParticipantSocketUpdater(userId, points, null,horseId, null));
        });
    }

    public void saveOrderNumber(int orderNumber, Long horseId, Long userId, Long contestId) {
        participantRepository.findByUserIdAndHorseIdAndContestId(userId, horseId, contestId).ifPresent(participant -> {
            participant.setOrderNumber(orderNumber);
            participantRepository.save(participant);
            simpMessagingTemplate.convertAndSend("/topic/participants/" + contestId, new ParticipantSocketUpdater(userId, null, null,horseId, orderNumber));
        });
    }

    public void assignUserToCompetition(Long competitionId){
        Competition competition = competitionService.getById(competitionId);
        participantRepository.save(Participant.builder()
                .competition(competition)
                .user(userService.getCurrentUser())
                .build());
    }

    public  List<Participant> getAssignmentsForCurrentUser(List<Long> competitionIds){
        return participantRepository.findByUserIdAndCompetitionIdIn(userService.getCurrentUser().getId(),competitionIds);
    }

    public Page<Competition> getCompetitionForCurrentUser(Pageable pageable){
        return  participantRepository.findByUserIdAndCompetitionIdIsNotNull(userService.getCurrentUser().getId(),pageable)
                .map(Participant::getCompetition);

    }

    public List<Participant> getCurrentParticipantsByCompetitionsContest(Long competitionId){
        return participantRepository.findByUserIdAndContestCompetitionId(userService.getCurrentUser().getId(),competitionId);
    }

    public void  deleteParticipant(Long id){
        participantRepository.deleteById(id);
    }

    public List<Participant> getByCycleId(Long cycleId){
        return participantRepository.findByCycleIdOrderByPointsDesc(cycleId);
    }
}
