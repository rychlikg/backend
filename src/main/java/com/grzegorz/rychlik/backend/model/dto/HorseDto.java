package com.grzegorz.rychlik.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grzegorz.rychlik.backend.model.HorseGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class HorseDto {
    private Long id;
    private String name;
    private String passport;
    private int age;
    private String country;
    private String race;
    private HorseGender gender;
    private String breeder;
    private String imgPath;
}
