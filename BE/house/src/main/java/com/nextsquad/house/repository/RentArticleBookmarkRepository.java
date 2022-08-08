package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticleBookmark;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentArticleBookmarkRepository extends JpaRepository<RentArticleBookmark, Long> {

    List<RentArticleBookmark> findByUser(User user);
}
