package com.example.project_1.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private SecretKey SCREAT=Keys.secretKeyFor(SignatureAlgorithm.HS256);;
	private final long EXPIRATION =1000 * 60 *60;
	

	public String generateToken(String username)
	{
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
				.signWith(SignatureAlgorithm.HS256, SCREAT)
				.compact();
		
	}
	
	public String extractUsername(String token)
	{
		return Jwts.parser().setSigningKey(SCREAT).parseClaimsJws(token).getBody().getSubject();
		
	}
	
	public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SCREAT).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	 
	
}
