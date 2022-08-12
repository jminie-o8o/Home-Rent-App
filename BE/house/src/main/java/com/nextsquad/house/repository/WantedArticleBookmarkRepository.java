package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WantedArticleBookmarkRepository extends JpaRepository<WantedArticleBookmark, Long> {
    List<WantedArticleBookmark> findByUser(User user);
}
