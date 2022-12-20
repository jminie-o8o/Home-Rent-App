package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentArticleBookmarkRepository extends JpaRepository<RentArticleBookmark, Long> {

    Page<RentArticleBookmark> findByUser(User user, Pageable pageable);

    Optional<RentArticleBookmark> findByUserAndRentArticle(User user, RentArticle rentArticle);

    @Query("SELECT b FROM RentArticleBookmark b WHERE b.user.id = :id")
    List<RentArticleBookmark> findByUserId(Long id);

    void deleteByRentArticle(RentArticle rentArticle);
}
