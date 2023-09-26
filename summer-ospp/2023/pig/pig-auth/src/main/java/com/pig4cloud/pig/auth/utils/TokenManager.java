package com.pig4cloud.pig.auth.utils;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {
	private long tokenExpiration = 24 * 60 * 60 * 1000;
	private final static String TOKEN_SIGN_KEY = "MAKKEY_PIG";

	public String createToken(String subject, String username, String id) {
		String token = Jwts.builder()
				.setSubject(subject)
				.claim("nickname", username)
				.claim("id", id)
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
				.signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
				.compressWith(CompressionCodecs.GZIP).compact();
		return token;
	}


	public String getUserFromToken(String token) {
		String user = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token).getBody().getSubject();
		return user;
	}

	public void removeToken(String token) {
		//jwttoken无需删除，客户端扔掉即可。
	}

}
