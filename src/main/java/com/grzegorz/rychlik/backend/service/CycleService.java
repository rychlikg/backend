package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Cycle;
import com.grzegorz.rychlik.backend.repository.CycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CycleService {
    private final CycleRepository cycleRepository;
    private final UserService userService;

    public void save(Cycle cycle){
        cycle.setUser(userService.getCurrentUser());
        cycleRepository.save(cycle);
    }

    public List<Cycle> userCycles(){
        return cycleRepository.findByUserId(userService.getCurrentUser().getId());
    }
    public Cycle getById(Long cycleId){
        return cycleRepository.getById(cycleId);
    }

    public Page<Cycle> getPage(Pageable pageable){
        return cycleRepository.findAll(pageable);
    }
}
