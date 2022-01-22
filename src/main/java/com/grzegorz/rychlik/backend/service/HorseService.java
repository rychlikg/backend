package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Horse;
import com.grzegorz.rychlik.backend.model.dao.User;
import com.grzegorz.rychlik.backend.repository.HorseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorseService {
    private final HorseRepository horseRepository;
    private final UserService userService;

    public Horse saveHorse(Horse horse) {
        horse.setUser(userService.getCurrentUser());
        return horseRepository.save(horse);
    }

    @Transactional
    public Horse updateHorse(Horse horse, Long horseId) {
        Horse horseDb = horseRepository.getById(horseId);
        horseDb.setName(horse.getName());
        horseDb.setAge(horse.getAge());
        horseDb.setPassport(horse.getPassport());
        return horseDb;
    }

    public List<Horse> getPage(Long userId) {
        return horseRepository.findByUserId(userId);
    }

    public void deleteById(Long horseId) {
        horseRepository.deleteById(horseId);
    }

    public Horse getUserHorseById(Long id, Long userId) {
        return horseRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Horse " + id + " does not exist"));
    }

    public List<Horse> getHorsesByCurrentUser(){
        User currentUser = userService.getCurrentUser();
        return horseRepository.findByUserId(currentUser.getId());
    }

    public Horse getById(Long id){
        return horseRepository.getById(id);
    }

    public List<Horse> findByName(String name){return horseRepository.findTop10ByNameContains(name);}

}
