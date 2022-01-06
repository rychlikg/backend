package com.grzegorz.rychlik.backend.model.dao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {
        @Index(name="idx_userId_horseId_contestId", columnList = "user_id,horse_id,contest_id", unique = true),
        @Index(name="idx_userId_competitionId", columnList = "user_id,competition_id", unique = true)
})
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @ToString.Exclude
    private Horse horse;

    @ManyToOne
    @ToString.Exclude
    private  Contest contest;

    @ManyToOne
    @ToString.Exclude
    private  Competition competition;

    private Integer points;
    private LocalTime roundTime;
    private int orderNumber;

    @ManyToOne
    @ToString.Exclude
    private Cycle cycle;
}
