package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Contest;
import com.grzegorz.rychlik.backend.model.dao.Horse;
import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.grzegorz.rychlik.backend.model.dao.User;
import com.grzegorz.rychlik.backend.model.dto.ParticipantSocketUpdater;
import com.grzegorz.rychlik.backend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ContestService contestService;
    private final HorseService horseService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessagingTemplate;

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
        return participantRepository.findByContestId(contestId, Sort.by(Sort.Order.asc("points"), Sort.Order.asc("roundTime")));
    }

    public void saveTime(String time, Long horseId, Long userId, Long contestId) {
        participantRepository.findByUserIdAndHorseIdAndContestId(userId, horseId, contestId).ifPresent(participant -> {
            String[] split = time.split(":");
            LocalTime localTime = LocalTime.of(0, Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            participant.setRoundTime(localTime);
            participantRepository.save(participant);
            simpMessagingTemplate.convertAndSend("/topic/participants/" + contestId, new ParticipantSocketUpdater(userId, null, localTime.toString(), horseId));
        });
    }

    public void savePoints(int points, Long horseId, Long userId, Long contestId) {
        participantRepository.findByUserIdAndHorseIdAndContestId(userId, horseId, contestId).ifPresent(participant -> {
            participant.setPoints(points);
            participantRepository.save(participant);
            simpMessagingTemplate.convertAndSend("/topic/participants/" + contestId, new ParticipantSocketUpdater(userId, points, null,horseId));
        });
    }
}
