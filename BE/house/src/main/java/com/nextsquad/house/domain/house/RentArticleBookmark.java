package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class RentArticleBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private RentArticle rentArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public RentArticleBookmark(RentArticle rentArticle, User user) {
        this.rentArticle = rentArticle;
        this.user = user;
    }
}
