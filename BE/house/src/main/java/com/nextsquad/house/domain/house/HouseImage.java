package com.nextsquad.house.domain.house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HouseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_image_id")
    private Long id;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_article_id")
    private RentArticle rentArticle;
    private int orderInList;

    public HouseImage(String storeFileUrl, RentArticle rentArticle, int orderInList) {
        this.imageUrl = storeFileUrl;
        this.rentArticle = rentArticle;
        this.orderInList = orderInList;
    }
}
