package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.SearchType;
import com.grzegorz.rychlik.backend.model.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private Map<SearchType, Function<String, List<SearchResultDto>>> strategyMap;
    private final CompetitionService competitionService;
    private final HorseService horseService;
    private final UserService userService;

    @PostConstruct
    public void init() {
        strategyMap = new EnumMap<>(SearchType.class);
        strategyMap.put(SearchType.COMPETITION, str -> competitionService.findByCompetitionName(str).stream()
                .map(competition -> SearchResultDto.builder()
                        .id(competition.getId())
                        .name(competition.getName())
                        .type(SearchType.COMPETITION)
                        .build())
                .collect(Collectors.toList()));

        strategyMap.put(SearchType.HORSE, str -> horseService.findByName(str).stream()
                .map(horse -> SearchResultDto.builder()
                        .id(horse.getId())
                        .name(horse.getName())
                        .type(SearchType.HORSE)
                        .build())
                .collect(Collectors.toList()));

        strategyMap.put(SearchType.PLAYER, str -> userService.findByFirstNameOrLastName(str).stream()
                .map(player -> SearchResultDto.builder()
                        .id(player.getId())
                        .name(player.getFirstName() + " " + player.getLastName())
                        .type(SearchType.PLAYER)
                        .build())
                .collect(Collectors.toList()));
    }

    public List<SearchResultDto> search(String text, SearchType type) {
        return strategyMap.get(type).apply(text);
    }
}
