package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Contest;
import com.grzegorz.rychlik.backend.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestService {

    private  final ContestRepository contestRepository;

    public void saveAll(List<Contest> list){
        contestRepository.saveAll(list);
    }

    public List<Contest> getContestByCompetition(Long competitionId){
        return contestRepository.findByCompetitionId(competitionId);
    }

    public Contest getById(Long id){
        return contestRepository.getById(id);
    }


}
