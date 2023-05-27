package com.cyrus.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cyrus.config.FaSystemException;
import com.cyrus.models.RedisDataDTO;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class MyJWTUtil {


    private final String SECRET_KEY = "56BA8B5D8E4414148831485B19E2C";

    public boolean verifyToken(String jwtToken, RedisDataDTO redisDataDTO) {
        DecodedJWT decodedJWT = null;
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("CYRUS")
                .build();
        try {
            decodedJWT = verifier.verify(jwtToken);
        } catch (Exception e) {
            throw new FaSystemException(30001, e.getMessage());
        }

        if (decodedJWT.getSubject().isEmpty())
            return false;

        return redisDataDTO.getUsername().equals(decodedJWT.getSubject());
    }

    public String createToken(String username) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String jwtToken = JWT.create()
                .withIssuer("CYRUS")
                .withSubject(username)
                .withClaim("userId", username)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);

        return jwtToken;
    }
}
