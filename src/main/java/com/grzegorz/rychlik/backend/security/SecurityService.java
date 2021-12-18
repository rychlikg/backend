package com.grzegorz.rychlik.backend.security;

import com.grzegorz.rychlik.backend.service.CompetitionService;
import com.grzegorz.rychlik.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final CompetitionService competitionService;

    public boolean hasAccessToCompetition(Long competitionId){
        return competitionService.getUserCompetitions().contains(competitionId);
    }
}
