package com.nextsquad.house.login.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OauthProperties {
    private String clientId;
    private String authServerUrl;
    private String resourceServerUrl;
    private String secretKey;
}
