package com.nextsquad.house.login.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MockClaim implements Claim {
    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public Boolean asBoolean() {
        return null;
    }

    @Override
    public Integer asInt() {
        return null;
    }

    @Override
    public Long asLong() {
        return 1L;
    }

    @Override
    public Double asDouble() {
        return null;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Date asDate() {
        return null;
    }

    @Override
    public <T> T[] asArray(Class<T> tClazz) throws JWTDecodeException {
        return null;
    }

    @Override
    public <T> List<T> asList(Class<T> tClazz) throws JWTDecodeException {
        return null;
    }

    @Override
    public Map<String, Object> asMap() throws JWTDecodeException {
        return null;
    }

    @Override
    public <T> T as(Class<T> tClazz) throws JWTDecodeException {
        return null;
    }
}
