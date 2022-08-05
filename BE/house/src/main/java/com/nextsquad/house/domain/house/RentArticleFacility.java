package com.nextsquad.house.domain.house;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class RentArticleFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_article_facility_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_article_id")
    private RentArticle rentArticle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility;

    public RentArticleFacility(RentArticle rentArticle, Facility facility) {
        this.rentArticle = rentArticle;
        this.facility = facility;
    }
}
