package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.house.RentArticle;

import javax.persistence.*;

@Entity
public class HouseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_image_id")
    private Long id;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    private RentArticle rentArticle;
    private int order;

}
