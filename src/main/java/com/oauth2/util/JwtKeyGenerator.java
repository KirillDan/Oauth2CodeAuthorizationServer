package com.oauth2.util;

import java.security.Key;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtKeyGenerator {
	public void generate() {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		

		String jwt = Jwts.builder().setClaims(Map.of("username", "")).signWith(key).compact();
	}
}
