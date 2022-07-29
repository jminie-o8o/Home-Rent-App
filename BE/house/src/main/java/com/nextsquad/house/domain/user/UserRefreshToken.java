package com.nextsquad.house.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash(value="token", timeToLive = 604800000)
@AllArgsConstructor
public class UserRefreshToken {

    @Id
    private String id;
    private String accountId;
    private String refreshToken;

}
