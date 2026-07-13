package com.felipe.gestaoativosti.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private final String secret;

    public TokenService(@Value("${api.security.token.secret}") String secret) {
        this.secret = secret;
    }

    public String gerarToken(String username){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("gestao-ativo-ti")
                .withSubject(username)
                .withExpiresAt(gerarDataExpiracao())
                .sign(algorithm);
    }

    public String getSubject(String tokenJWT){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("gestao-ativo-ti")
                .build()
                .verify(tokenJWT)
                .getSubject();
    }

    private Instant gerarDataExpiracao(){
        return Instant.now().plus(24, ChronoUnit.HOURS);
    }

}
