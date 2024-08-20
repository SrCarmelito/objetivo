package com.objetivo.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.objetivo.auth.domain.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String gerarToken(Usuario usuario, int minutes) {
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withClaim("id", usuario.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(minutes)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("24c191160-f816-4385-8e98-5f9e3438060c"));
    }

    public Object getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("24c191160-f816-4385-8e98-5f9e3438060c"))
                .build().verify(token).getSubject();
    }

}
