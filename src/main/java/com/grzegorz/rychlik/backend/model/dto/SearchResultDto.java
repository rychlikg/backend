package com.grzegorz.rychlik.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grzegorz.rychlik.backend.model.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResultDto {
    private Long id;
    private String name;
    private SearchType type;

}
