package com.example.board_server.Utils;

import com.example.board_server.exceptions.ExpiredTokenException;
import com.example.board_server.exceptions.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static String SECURITY_KEY = System.getenv("SECRET_KEY_BASE");

    private String generateToken(Object data, Long expire, String type) {
        long nowMillis = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(nowMillis))
                .setSubject(data.toString())
                .claim("type", type)
                .setExpiration(new Date(nowMillis + expire))
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY.getBytes());

        return builder.compact();
    }

    public String getAccessToken(Object data) {

        return generateToken(data, 1000L * 3600 * 24, "access_token");
    }
    public String getRefreshToken(Object data) {

        return generateToken(data, 1000L * 3600 * 24 * 30, "refresh_token");
    }

    public Integer parseToken(String token) {
        String result;
        try {
            result = Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().getSubject();
            if (!Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().get("type").equals("access_token")) throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        }
        return Integer.parseInt(result);
    }

    public Integer parseRefreshToken(String token) {
        try {
            if(!Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().get("type").equals("refresh_token")) throw new InvalidTokenException();
            token = Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        }
        return Integer.parseInt(token);
    }
}
