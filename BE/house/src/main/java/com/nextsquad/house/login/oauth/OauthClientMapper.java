package com.nextsquad.house.login.oauth;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OauthClientMapper {
    private Map<String, OauthClient> clientMap;

    public OauthClientMapper(Map<String, OauthClient> clientMap) {
        this.clientMap = new HashMap<>(clientMap);
    }
    public Optional<OauthClient> getOauthClient(String oauthClientType) {
        return Optional.of(clientMap.get(oauthClientType));
    }
}
