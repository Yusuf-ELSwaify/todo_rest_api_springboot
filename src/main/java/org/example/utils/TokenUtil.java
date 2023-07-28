package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.persistence.models.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokenUtil {
	//private final String CLAIMS_SUBJECT = "sub";
	//private final String CLAIMS_CREATED = "created";

	@Value("${auth.expiration}")
	private Long TOKEN_VALIDITY;

	@Value("${auth.secret}")
	private String TOKEN_SECRET;
	public String generateToken(AppUser userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("ID", userDetails.getId());

//		claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
//		claims.put(CLAIMS_CREATED, new Date());

		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(generateExpirationDate())
				//.signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + TOKEN_VALIDITY);
	}
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(TOKEN_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	public String getUsernameFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
