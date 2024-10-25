package com.cognixia.stagestream.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTUtil {

	@Value("${jwt_secret}")
	private String secret;

    public String generateToken(String username) {
        return JWT.create()
            .withSubject(username)
            .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        return verifier.verify(token);
    }

    public String extractUsername(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }
}