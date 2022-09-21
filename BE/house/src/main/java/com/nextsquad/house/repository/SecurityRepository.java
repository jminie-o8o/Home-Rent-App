package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.SecurityFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<SecurityFacility, Long> {
    Optional<SecurityFacility> findByName(String name);
}
