package com.reclaimit.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtKeyProvider {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Key getKey() {
        return KEY;
    }
}
