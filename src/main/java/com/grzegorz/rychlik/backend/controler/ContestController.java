package com.grzegorz.rychlik.backend.controler;


import com.grzegorz.rychlik.backend.mapper.ContestMapper;
import com.grzegorz.rychlik.backend.model.dto.ContestDto;
import com.grzegorz.rychlik.backend.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;
    private final ContestMapper contestMapper;

    @GetMapping("/{competitionId}")
    public List<ContestDto> getContestByCompetitionId(@PathVariable Long competitionId){
        return contestMapper.toDtoList(contestService.getContestByCompetition(competitionId));
    }
}
