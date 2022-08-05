package com.nextsquad.house.domain.house;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class SecurityFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_facility_id")
    private Long id;
    private String name;
}
