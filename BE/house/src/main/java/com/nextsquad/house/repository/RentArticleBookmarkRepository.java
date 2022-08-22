package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentArticleBookmarkRepository extends JpaRepository<RentArticleBookmark, Long> {

    Page<RentArticleBookmark> findByUser(User user, Pageable pageable);

    Optional<RentArticleBookmark> findByUserAndRentArticle(User user, RentArticle rentArticle);

    List<RentArticleBookmark> findListByUser(User user);
}
