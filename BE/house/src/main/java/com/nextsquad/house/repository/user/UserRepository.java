package com.nextsquad.house.repository.user;

import com.nextsquad.house.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccountId(String accountId);

    Boolean existsUserByDisplayName(String displayName);
}
