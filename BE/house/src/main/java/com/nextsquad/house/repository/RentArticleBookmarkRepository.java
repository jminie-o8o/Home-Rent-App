package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentArticleBookmarkRepository extends JpaRepository<RentArticleBookmark, Long> {

    List<RentArticleBookmark> findByUser(User user);

    Optional<RentArticleBookmark> findByUserAndRentArticle(User user, RentArticle rentArticle);
}
