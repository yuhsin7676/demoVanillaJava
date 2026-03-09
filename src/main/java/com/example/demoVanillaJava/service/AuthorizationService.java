package com.example.demoVanillaJava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoVanillaJava.shared.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class AuthorizationService {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final Algorithm algorithm = Algorithm.HMAC256("jwt-secret-123456789012345678901");

    public boolean authorization(String b64jwt) throws JsonProcessingException {
        if ("Bearer ".equals(b64jwt.substring(0, 7)))
            b64jwt = b64jwt.substring(7);
        else
            return false;
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(b64jwt);
            String payload = new String(Base64.getDecoder().decode(jwt.getPayload()));
            LoginDto loginDto = objectMapper.readerFor(LoginDto.class).readValue(payload);
            return "admin".equals(loginDto.getLogin());
        } catch (TokenExpiredException e) {
            return false;
        }
    }

}
