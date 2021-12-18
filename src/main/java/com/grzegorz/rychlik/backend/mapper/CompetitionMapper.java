package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Competition;
import com.grzegorz.rychlik.backend.model.dto.CompetitionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = UserMapper.class)
public interface CompetitionMapper {

    @Mapping(source = "user.firstName", target = "organizer.firstName")
    @Mapping(source = "user.lastName", target = "organizer.lastName")
    @Mapping(target = "organizer.roles", ignore = true)
    @Mapping(target = "organizer.password", ignore = true)
    CompetitionDto toDto(Competition competition);
    Competition toDao(CompetitionDto competitionDto);
}
