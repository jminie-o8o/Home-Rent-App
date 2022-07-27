package com.nextsquad.house.repository;

import com.nextsquad.house.domain.user.UserRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, String> {

}
