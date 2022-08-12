package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


public interface WantedArticleBookmarkRepository extends JpaRepository<WantedArticleBookmark, Long> {
    List<WantedArticleBookmark> findByUser(User user);
    Optional<WantedArticleBookmark> findByUserAndWantedArticle(User user, WantedArticle wantedArticle);
}
