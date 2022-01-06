package com.grzegorz.rychlik.backend.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String name;
    private LocalDate startDate;
    private  LocalDate endDate;
    private LocalDate deadline;

    @OneToMany(mappedBy = "competition", orphanRemoval = true, fetch = FetchType.EAGER, cascade =CascadeType.ALL)
    private List<Contest> contests;

    private String description;

    @ManyToOne
    private Cycle cycle;


}
