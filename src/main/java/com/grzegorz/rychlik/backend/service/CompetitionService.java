package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Competition;
import com.grzegorz.rychlik.backend.model.dao.Contest;
import com.grzegorz.rychlik.backend.repository.CompetitionRepository;
import com.grzegorz.rychlik.backend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final  ContestService contestService;
    private final  UserService userService;
    private final CycleService cycleService;

    @Transactional
    public Competition save(Competition competition, List<Contest> list, Long cycleId){
        competition.setUser(userService.getCurrentUser());
        if(cycleId != null){
            competition.setCycle(cycleService.getById(cycleId));
        }
        competitionRepository.save(competition);
        list.forEach(contest -> contest.setCompetition(competition));
        contestService.saveAll(list);
        return competition;
}

    public Page<Competition> getPage(Pageable pageable){
        return competitionRepository.findAll(pageable);
    }

    public Competition getById(Long id){
        return competitionRepository.getById(id);
    }

    public void update(Competition competition, List<Contest> contests, Long id){
        Competition competitionDb = getById(id);
        competitionDb.setName(competition.getName());
        competitionDb.setStartDate(competition.getStartDate());
        competitionDb.setEndDate(competition.getEndDate());
        competitionDb.setDescription(competition.getDescription());
        contests.forEach(contest -> {
            contest.setCompetition(competitionDb);
            //if(contest.getParticipants()==null){
            //contest.setParticipants(new ArrayList<>());}
        });
        competitionDb.getContests().clear();
        competitionDb.getContests().addAll(contests);
      //  contestService.saveAll(contests);
        competitionRepository.save(competitionDb);
    }

    public List<Long> getUserCompetitions(Long userId){
        return competitionRepository.findByUserId(userId).stream()
                .map(Competition::getId)
                .collect(Collectors.toList());
    }

    public List<Long> getUserCompetitions(){
        return competitionRepository.findByUserEmail(SecurityUtils.getCurrentUserEmail()).stream()
                .map(Competition::getId)
                .collect(Collectors.toList());
    }

    public  Page<Competition> getCompetitionByCurrentUser(Pageable pageable){
        return competitionRepository.findByUserEmail(SecurityUtils.getCurrentUserEmail(),pageable);
    }

    public List<Competition> getByCycleId(Long cycleId){
        return  competitionRepository.findByCycleId(cycleId);
    }

    public List<Competition> findByCompetitionName(String name){
        return competitionRepository.findTop10ByNameContains(name);
    }

    public  List<Competition> getCompetitionFromCurrentWeek(){
        //LocalDate monday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        System.out.println(sunday);
        System.out.println(LocalDate.now());

        return competitionRepository.findByStartDateBetweenOrStartDate(LocalDate.now(),sunday,LocalDate.now());

    }

    public  List<Competition> getPreviousCompetitions(){
        return competitionRepository.findByStartDateBeforeOrStartDate(LocalDate.now(),LocalDate.now());
    }
    public  Page<Competition> getAfterCompetitions(Pageable pageable) {
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        //System.out.println(sunday);

        return competitionRepository.findByStartDateAfter(sunday,pageable);
    }
}
