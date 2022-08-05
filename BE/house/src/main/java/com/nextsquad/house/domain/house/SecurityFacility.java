package com.nextsquad.house.domain.house;

import javax.persistence.*;

@Entity

public class SecurityFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "security_facility_id")
    private Long id;
    private String name;
}
