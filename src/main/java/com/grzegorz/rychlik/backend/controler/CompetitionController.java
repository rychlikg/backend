package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.CompetitionMapper;
import com.grzegorz.rychlik.backend.mapper.ContestMapper;
import com.grzegorz.rychlik.backend.model.dto.CompetitionDto;
import com.grzegorz.rychlik.backend.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;
    private final ContestMapper contestMapper;

    @PostMapping
    public CompetitionDto saveCompetition(@RequestBody CompetitionDto competitionDto) {
       return competitionMapper.toDto(competitionService.save(competitionMapper.toDao(competitionDto), contestMapper.toDaoList(competitionDto.getContests())));
    }

    @GetMapping
    public Page<CompetitionDto> getCompetitionPage(@RequestParam int page, @RequestParam int size){
        return competitionService.getPage(PageRequest.of(page,size)).map(competitionMapper::toDto);
    }

    @GetMapping("/{id}")
    public CompetitionDto getCompetitionById(@PathVariable Long id){
        return competitionMapper.toDto(competitionService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && @securityService.hasAccessToCompetition(#id)")
    public void updateCompetition(@RequestBody CompetitionDto competitionDto, @PathVariable Long id){
        competitionService.update(competitionMapper.toDao(competitionDto),contestMapper.toDaoList((competitionDto.getContests())),id);
    }
}
