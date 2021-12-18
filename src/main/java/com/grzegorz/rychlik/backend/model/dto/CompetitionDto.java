package com.grzegorz.rychlik.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetitionDto {
    private Long id;

    private String name;
    private LocalDate startDate;
    private  LocalDate endDate;
    private List<ContestDto> contests;
    private  UserDto organizer;

    private String description;
}
