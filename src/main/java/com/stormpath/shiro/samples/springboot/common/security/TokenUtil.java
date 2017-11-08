package com.stormpath.shiro.samples.springboot.common.security;

/****************************************************************************************
 * Copyright (C) 2016-2017 Solace Inc.
 *
 * This file is part of the MaaS
 *
 * MaaS source files can not be copied and/or distributed without the express
 * permission of Solace Inc.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 ****************************************************************************************/

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;
import java.util.Map;

public class TokenUtil {

    private static final int bearerTokenStartIndex = 7;
    static final String CLAIM_KEY_USERID = "sub";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_ROLES = "roles";
    static final String CLAIM_KEY_ORG = "org";
    static final String CLAIM_KEY_EMAIL = "email";
    static final String CLAIM_KEY_FIRSTNAME = "fname";
    static final String CLAIM_KEY_LASTNAME = "lname";

    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public String getUserIdFromAuthToken(String authToken, String secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return new MaaSJwtParser().setSigningAlgorithm(signatureAlgorithm).setSigningKey(secret).parseClaimsJws(authToken).getBody().getSubject();
    }

    public String getOrgIdFromToken(String authToken, String secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return (String) new MaaSJwtParser().setSigningAlgorithm(signatureAlgorithm).setSigningKey(secret).parseClaimsJws(authToken).getBody().get(CLAIM_KEY_ORG);
    }

    public String getEmailFromToken(String authToken, String secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return (String) new MaaSJwtParser()
                .setSigningAlgorithm(signatureAlgorithm)
                .setSigningKey(secret)
                .parseClaimsJws(authToken)
                .getBody()
                .get(CLAIM_KEY_EMAIL);
    }

    public String getFirstNameFromToken(String authToken, String secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return (String) new MaaSJwtParser()
                .setSigningAlgorithm(signatureAlgorithm)
                .setSigningKey(secret)
                .parseClaimsJws(authToken)
                .getBody()
                .get(CLAIM_KEY_FIRSTNAME);
    }

    public String getLastNameFromToken(String authToken, String secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return (String) new MaaSJwtParser()
                .setSigningAlgorithm(signatureAlgorithm)
                .setSigningKey(secret)
                .parseClaimsJws(authToken)
                .getBody()
                .get(CLAIM_KEY_LASTNAME);
    }


    public String generateChangePasswordToken(String userId, String email, Long expiration, String secret) throws IllegalStateException, SignatureException, IllegalArgumentException {

        return new MaaSJwtBuilder()
                .setClaims(getChangePasswordClaims(userId, email))
                .setExpiration(generateExpirationDate(expiration))
                .signWith(signatureAlgorithm, secret)
                .compact();
    }


    public boolean validateTokenAgainstSignature(String token, String secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        new MaaSJwtParser().setSigningAlgorithm(signatureAlgorithm)
                            .setSigningKey(secret)
                            .parseClaimsJws(token);
        return true;
    }

    private Date generateExpirationDate(Long expiration) {
        return new Date(System.currentTimeMillis() + expiration);
    }

    private Map<String, Object> getChangePasswordClaims(String userId, String email) {
        Claims claims = new DefaultClaims();
        claims.put(CLAIM_KEY_USERID, userId);
        claims.put(CLAIM_KEY_EMAIL, email);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return claims;
    }

}