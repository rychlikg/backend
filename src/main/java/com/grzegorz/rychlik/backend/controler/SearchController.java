package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.model.SearchType;
import com.grzegorz.rychlik.backend.model.dto.SearchResultDto;
import com.grzegorz.rychlik.backend.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public List<SearchResultDto> search(@RequestParam String text, @RequestParam SearchType type){
        return searchService.search(text,type);
    }
}
