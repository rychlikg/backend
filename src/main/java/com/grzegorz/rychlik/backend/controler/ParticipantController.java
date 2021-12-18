package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.ParticipantMapper;
import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.grzegorz.rychlik.backend.model.dto.AssignParticipantDto;
import com.grzegorz.rychlik.backend.model.dto.ParticipantDto;
import com.grzegorz.rychlik.backend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;

    @PostMapping
    public void assignUserToContest(@RequestBody AssignParticipantDto assignParticipantDto){
        participantService.assignToContest(assignParticipantDto.getContestId(), assignParticipantDto.getHorseId());
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
}
