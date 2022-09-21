package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WantedArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private String address;
    private boolean isCompleted;
    private String title;
    private String content;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private int rentBudget;
    private int depositBudget;
    private int viewCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime modifiedAt;
    private boolean isDeleted;

    @OneToMany(mappedBy = "wantedArticle")
    private List<WantedArticleBookmark> bookmarks = new ArrayList<>();

    public void markAsDeleted(){
        isDeleted = true;
    }

    public void modifyArticle(WantedArticleRequest request) {
        this.address = request.getAddress();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.moveInDate = request.getMoveInDate();
        this.moveOutDate = request.getMoveOutDate();
        this.rentBudget = request.getRentBudget();
        this.depositBudget = request.getDepositBudget();
        this.modifiedAt = LocalDateTime.now();
    }

    public void addViewCount(){
        viewCount++;
    }
}
