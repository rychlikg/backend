package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.config.properties.FilePropertiesConfig;
import com.grzegorz.rychlik.backend.model.dao.Horse;
import com.grzegorz.rychlik.backend.model.dao.User;
import com.grzegorz.rychlik.backend.repository.HorseRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorseService {
    private final HorseRepository horseRepository;
    private final UserService userService;
    private final FilePropertiesConfig filePropertiesConfig;

    @SneakyThrows
    public Horse saveHorse(Horse horse, MultipartFile img) {
        System.out.println("Weszło2");
        horse.setUser(userService.getCurrentUser());
        horseRepository.save(horse);
        if (img != null) {
            System.out.println("Weszło");
            String fileName = "horse" + horse.getId() + ".png";
            Path path = Paths.get(filePropertiesConfig.getHorse(), fileName);
            Files.copy(img.getInputStream(), path);
            horse.setImgPath("/image/" + fileName);

        }
        return horseRepository.save(horse);
    }

    @Transactional
    @SneakyThrows
    public Horse updateHorse(Horse horse, Long horseId, MultipartFile img) {
        Horse horseDb = horseRepository.getById(horseId);
        horseDb.setName(horse.getName());
        horseDb.setAge(horse.getAge());
        horseDb.setPassport(horse.getPassport());
        horseDb.setBreeder(horse.getBreeder());
        horseDb.setRace(horse.getRace());
        horseDb.setCountry(horse.getCountry());
        horseDb.setGender(horse.getGender());
        if(img != null){
            String fileName = "horse" + horseId + ".png";
            Path path = Paths.get(filePropertiesConfig.getHorse(), fileName);
            Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            horseDb.setImgPath("/image/" + fileName);
        }

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
