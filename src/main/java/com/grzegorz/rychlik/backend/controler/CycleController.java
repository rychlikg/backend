package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.CycleMapper;
import com.grzegorz.rychlik.backend.model.dto.CycleDto;
import com.grzegorz.rychlik.backend.repository.CycleRepository;
import com.grzegorz.rychlik.backend.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cycle")
@RequiredArgsConstructor
public class CycleController {
    private final CycleService cycleService;
    private final CycleMapper cycleMapper;

    @PostMapping
    public void saveCycle(@RequestBody CycleDto cycleDto){
        cycleService.save(cycleMapper.toDao(cycleDto));
    }

    @GetMapping("/current-user")
    public List<CycleDto> getCyclesForCurrentUser(){
        return cycleMapper.toDto(cycleService.userCycles());
    }

    @GetMapping
    public Page<CycleDto> getCyclePage(@RequestParam int page, @RequestParam int size){
        return cycleService.getPage(PageRequest.of(page,size)).map(cycleMapper :: toDto);
    }

    @GetMapping("/{id}")
    public CycleDto getCycleById(@PathVariable Long id){
        return cycleMapper.toDto(cycleService.getById(id));
    }


}
