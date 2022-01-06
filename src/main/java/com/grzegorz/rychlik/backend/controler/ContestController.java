package com.grzegorz.rychlik.backend.controler;


import com.grzegorz.rychlik.backend.mapper.ContestMapper;
import com.grzegorz.rychlik.backend.model.dto.ContestDto;
import com.grzegorz.rychlik.backend.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;
    private final ContestMapper contestMapper;

    @GetMapping("/competition/{competitionId}")
    public List<ContestDto> getContestByCompetitionId(@PathVariable Long competitionId){
        return contestMapper.toDtoList(contestService.getContestByCompetition(competitionId));
    }

    @PatchMapping("/start")
    public void changeContestStart(@RequestBody ContestDto contestDto){
        contestService.setContestTime(contestMapper.toDao(contestDto));
    }

    @PatchMapping("/finished")
    public void changeContestFinished(@RequestBody ContestDto contestDto){
        contestService.setFinished(contestMapper.toDao(contestDto));
    }

    @GetMapping("/{id}")
    public ContestDto getContestById(@PathVariable Long id){
        return contestMapper.toDto(contestService.getById(id));
    }
}
