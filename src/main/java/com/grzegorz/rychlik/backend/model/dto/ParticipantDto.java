package com.grzegorz.rychlik.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipantDto {

    private String firstName;
    private String lastName;
    private String horseName;
    private Integer points;
    private String roundTime;
    private Long userId;
    private Long horseId;
    private Long contestId;
    private Long organizerId;
}
