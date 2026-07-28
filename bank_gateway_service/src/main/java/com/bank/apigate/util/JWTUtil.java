package com.bank.apigate.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
	private static final String SECRET ="MySecretKeyForJwtAuthentication1234567890987654321";
	private final  SecretKey key=Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	public String generateToken(){
		return Jwts.builder()
		        .setSubject("senthil")
		        .setIssuedAt(new Date())
		        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
		        .signWith(key, SignatureAlgorithm.HS256)
		        .compact();
	}

}
