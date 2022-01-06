package com.grzegorz.rychlik.backend.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ContestDto {
    private Long id;
    private String name;
    private int height;
    private int amountObstacles;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;
    private boolean finished;

}
