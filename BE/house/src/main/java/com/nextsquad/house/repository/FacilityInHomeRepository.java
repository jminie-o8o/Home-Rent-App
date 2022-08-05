package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticleFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityInHomeRepository extends JpaRepository<RentArticleFacility, Long> {
}
