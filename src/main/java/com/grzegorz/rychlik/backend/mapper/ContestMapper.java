package com.grzegorz.rychlik.backend.mapper;

import com.grzegorz.rychlik.backend.model.dao.Contest;
import com.grzegorz.rychlik.backend.model.dto.ContestDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContestMapper {
    ContestDto toDto(Contest contest);
    Contest toDao(ContestDto contestDto);
    List<Contest> toDaoList(List<ContestDto> list);
    List<ContestDto> toDtoList(List<Contest> list);
}
