package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Cycle;
import com.grzegorz.rychlik.backend.model.dto.CycleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CycleMapper {
    Cycle toDao(CycleDto cycleDto);
    CycleDto toDto(Cycle cycle);

    List<CycleDto> toDto(List<Cycle> cycle);
}
