package com.grzegorz.rychlik.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.models.auth.In;
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

    private Long id;
    private String firstName;
    private String lastName;
    private String horseName;
    private Integer points;
    private String roundTime;
    private Long userId;
    private Long horseId;
    private Long contestId;
    private Long organizerId;
    private  String contestName;
    private int height;
    private int orderNumber;
}
