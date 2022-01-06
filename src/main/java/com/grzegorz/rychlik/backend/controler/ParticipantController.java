package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.CompetitionMapper;
import com.grzegorz.rychlik.backend.mapper.ParticipantMapper;
import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.grzegorz.rychlik.backend.model.dto.AssignParticipantDto;
import com.grzegorz.rychlik.backend.model.dto.CompetitionDto;
import com.grzegorz.rychlik.backend.model.dto.ParticipantDto;
import com.grzegorz.rychlik.backend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;
    private final CompetitionMapper competitionMapper;

    @PostMapping("/contest")
    public void assignUserToContest(@RequestBody AssignParticipantDto assignParticipantDto){
        participantService.assignToContest(assignParticipantDto.getContestId(), assignParticipantDto.getHorseId());
    }

    @PostMapping("/competition")
    public void assignUserToCompetition(@RequestBody AssignParticipantDto assignParticipantDto){
        participantService.assignUserToCompetition(assignParticipantDto.getCompetitionId());
    }

    @GetMapping
    public List<ParticipantDto> getParticipants(@RequestParam Long contestId){
        return participantMapper.toDtoList(participantService.getParticipantsByContestId(contestId));
    }

    @PatchMapping("/time")
    public void updateParticipantRoundTime(@RequestBody ParticipantDto participantDto){
        participantService.saveTime(participantDto.getRoundTime(),participantDto.getHorseId(),participantDto.getUserId(),participantDto.getContestId());
    }

    @PatchMapping("/points")
    public void updateParticipantPoints(@RequestBody ParticipantDto participantDto){
        participantService.savePoints(participantDto.getPoints(),participantDto.getHorseId(),participantDto.getUserId(),participantDto.getContestId());
    }

    @PatchMapping("/order-number")
    public void updateParticipantOrderNumber(@RequestBody ParticipantDto participantDto){
        participantService.saveOrderNumber(participantDto.getOrderNumber(),participantDto.getHorseId(),participantDto.getUserId(),participantDto.getContestId());
    }

    @GetMapping("/competitions")
    public List<Long> getParticipantsCompetitionsByCompetitionId(@RequestParam List<Long> competitionIds){
        return participantMapper.toCompetitionIds(participantService.getAssignmentsForCurrentUser(competitionIds));
    }

    @GetMapping("/current-competition")
    public Page<CompetitionDto> getCompetitionFotCurrentUser(@RequestParam int page, @RequestParam int size){
        return  participantService.getCompetitionForCurrentUser(PageRequest.of(page,size, Sort.by(Sort.Order.desc("Id")))).map(competitionMapper::toDto);
    }

    @GetMapping("/current-contests/{competitionId}")
    public  List<ParticipantDto> getCurrentParticipantsByCompetitionContest(@PathVariable Long competitionId){
        return participantMapper.toDtoList(participantService.getCurrentParticipantsByCompetitionsContest(competitionId));
    }

    @DeleteMapping("/{id}")
    public  void  deleteParticipant(@PathVariable Long id){
        participantService.deleteParticipant(id);
    }

    @GetMapping("/cycle/{cycleId}")
    public List<ParticipantDto> getParticipantsByCycleId(@PathVariable Long cycleId){
        return participantMapper.toDtoList(participantService.getByCycleId(cycleId));
    }
}
