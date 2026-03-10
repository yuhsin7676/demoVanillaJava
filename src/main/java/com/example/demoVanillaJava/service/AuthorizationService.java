package com.example.demoVanillaJava.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demoVanillaJava.shared.dto.LoginDto;
import com.example.demoVanillaJava.shared.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

public class AuthorizationService {

    private static final AuthorizationService service = new AuthorizationService();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final Algorithm algorithm = Algorithm.HMAC256("jwt-secret-123456789012345678901");
    private final UserService userService = new UserService(); // TODO Создаётся второй UserService

    private AuthorizationService() {}
    public static AuthorizationService getInstance() {
        return service;
    }

    public String authentication(LoginDto loginDto) {

        UserDto userDto = userService.getByLogin(loginDto);
        if (userDto == null || !Objects.equals(sha256Hash(loginDto.getPassword()), userDto.getPassword())) return null;

        return "Bearer " + JWT.create()
                .withHeader(Map.of(
                        "alg", "HS256",
                        "typ", "JWT"))
                .withPayload(Map.of(
                        "login", loginDto.getLogin(),
                        "exp", Instant.now().plusSeconds(3600)))
                .sign(algorithm);
    }

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

    public String sha256Hash(String password) {
        if (password == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException ignored) {
            throw new RuntimeException();
        }
    }

}
