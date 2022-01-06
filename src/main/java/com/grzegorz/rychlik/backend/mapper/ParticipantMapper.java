package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.grzegorz.rychlik.backend.model.dto.ParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "horse.name", target = "horseName")
    @Mapping(source = "horse.id", target = "horseId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "contest.competition.user.id",target = "organizerId")
    @Mapping(source = "contest.name", target = "contestName")
    @Mapping(source = "contest.height", target = "height")
    @Mapping(source = "orderNumber",target = "orderNumber")
    @Mapping(source = "id",target = "id")
    ParticipantDto toDto(Participant participant);
    List<ParticipantDto> toDtoList(List<Participant> participants);
    default List<Long> toCompetitionIds(List<Participant> participants){
        return participants.stream()
                .map(participant -> participant.getCompetition().getId())
                .collect(Collectors.toList());
    }
}
