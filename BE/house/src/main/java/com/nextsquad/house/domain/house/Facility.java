package com.nextsquad.house.domain.house;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;
    private String name;
}
