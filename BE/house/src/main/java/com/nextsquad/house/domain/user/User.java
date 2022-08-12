package com.nextsquad.house.domain.user;

import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.dto.UserInfoDto;
import com.nextsquad.house.login.oauth.OauthClientType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private Long id;
    private String accountId;
    private String displayName;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private OauthClientType oauthClientType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<RentArticleBookmark> rentArticleBookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<WantedArticle> wantedArticles = new ArrayList<>();

    public User(String accountId, String displayName, String profileImageUrl, OauthClientType oauthClientType) {
        this.accountId = accountId;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.oauthClientType = oauthClientType;
    }

    public void modifyInfo(UserInfoDto userInfoDto){
        this.displayName = userInfoDto.getDisplayName();
        this.profileImageUrl = userInfoDto.getProfileImageUrl();
        this.gender = Gender.valueOf(userInfoDto.getGender());
    }
}

