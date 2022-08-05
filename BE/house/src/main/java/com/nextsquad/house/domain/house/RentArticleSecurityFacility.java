package com.nextsquad.house.domain.house;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class RentArticleSecurityFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private RentArticle rentArticle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private SecurityFacility securityFacility;

    public RentArticleSecurityFacility(RentArticle rentArticle, SecurityFacility securityFacility) {
        this.securityFacility = securityFacility;
        this.rentArticle = rentArticle;
    }
}
