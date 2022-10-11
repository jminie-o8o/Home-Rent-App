package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class WantedArticleBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private WantedArticle wantedArticle;

    public WantedArticleBookmark(User user, WantedArticle wantedArticle) {
        this.user = user;
        this.wantedArticle = wantedArticle;
    }
}
