package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticleSecurityFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityInHomeRepository extends JpaRepository<RentArticleSecurityFacility, Long> {
}
