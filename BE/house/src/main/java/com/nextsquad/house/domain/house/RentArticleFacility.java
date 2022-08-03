package com.nextsquad.house.domain.house;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor

public class RentArticleFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_article_facility_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private RentArticle rentArticle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Facility facility;

    public RentArticleFacility(RentArticle rentArticle, Facility facility) {
        this.rentArticle = rentArticle;
        this.facility = facility;
    }
}
