package com.grzegorz.rychlik.backend.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int height;
    private int amountObstacles;

    //@OneToMany(mappedBy = "contest", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //private List<Participant> participants;

    @ManyToOne
    private Competition competition;
}
