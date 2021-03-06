package com.grzegorz.rychlik.backend.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Horse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String passport;
    private int age;
    private String country;
    private String race;
    private String gender;
    private String breeder;
    private String imgPath;

    @ManyToOne
    private User user;

}
