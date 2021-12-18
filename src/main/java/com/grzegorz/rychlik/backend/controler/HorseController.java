package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.HorseMapper;
import com.grzegorz.rychlik.backend.model.dto.HorseDto;
import com.grzegorz.rychlik.backend.model.dto.UserDto;
import com.grzegorz.rychlik.backend.service.HorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horses")
@RequiredArgsConstructor
public class HorseController {
    private final HorseMapper horseMapper;
    private final HorseService horseService;

    @PostMapping
    public HorseDto saveHorse(@RequestBody HorseDto horse) {
        return horseMapper.toDto(horseService.saveHorse(horseMapper.toDao(horse)));
    }

    @PutMapping("/{id}")
    public HorseDto updateHorse(@RequestBody HorseDto horse, @PathVariable Long id) {
        return horseMapper.toDto(horseService.updateHorse(horseMapper.toDao(horse), id));
    }

    @DeleteMapping("/{id}")
    public void deleteHorse(@PathVariable Long id) {
        horseService.deleteById(id);
    }

    @GetMapping
    public List<HorseDto> getHorsePage(@RequestParam Long userId) {
        return horseMapper.toListDto(horseService.getPage(userId));
    }

    @GetMapping("/user")
    public List<HorseDto> getHorsePageByCurrentUser() {
        return horseMapper.toListDto(horseService.getHorsesByCurrentUser());
    }

    @GetMapping("/{id}")
    public  HorseDto getHorseById(@PathVariable Long id){
        return horseMapper.toDto(horseService.getById(id));
    }

}
