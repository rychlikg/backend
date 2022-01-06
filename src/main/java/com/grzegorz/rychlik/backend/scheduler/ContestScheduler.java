package com.grzegorz.rychlik.backend.scheduler;

import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.grzegorz.rychlik.backend.repository.ContestRepository;
import com.grzegorz.rychlik.backend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContestScheduler {
    private final ParticipantRepository participantRepository;
    private final ContestRepository contestRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    @Transactional
    public void sumCyclePoints() {
        List<Participant> participants = participantRepository.findByContestFinishedIsTrueAndContestCountedIsFalse();
        Map<Long, List<Participant>> participantByContestId = participants.stream()
                .collect(Collectors.groupingBy(participant -> participant.getContest().getId()));
        Map<Long, Participant> participantMap = new HashMap<>();
        participantByContestId.forEach((key, value) -> {
            value.sort(Comparator.comparing(Participant::getPoints).reversed().thenComparing(Participant::getRoundTime).reversed());
            Integer points = 10;
            for (Participant participant : value) {
                Participant cycleParticipant = participantMap.get(participant.getUser().getId());
                if (cycleParticipant == null) {
                    cycleParticipant = participantRepository.findByUserIdAndCycleId(participant.getUser().getId(), participant.getContest().getCompetition().getCycle().getId())
                            .orElse(Participant.builder()
                                    .user(participant.getUser())
                                    .cycle(participant.getContest().getCompetition().getCycle())
                                    .points(0)
                                    .build());
                }

                cycleParticipant.setPoints(cycleParticipant.getPoints() + points);

                participantMap.put(participant.getUser().getId(), cycleParticipant);

                if (points > 0) {
                    points--;
                }

            }
        });

        participantRepository.saveAll(participantMap.values());
        participantByContestId.keySet().forEach(contestRepository::updateCountedField);
    }
}
