package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Horse;
import com.grzegorz.rychlik.backend.model.dto.HorseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorseMapper {
    HorseDto toDto(Horse horse);
    Horse toDao(HorseDto horseDto);
    List<HorseDto> toListDto(List<Horse> horseList);
}
