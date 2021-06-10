package bg.assignment.bg.backend.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// code from https://javatechonline.com/how-to-implement-jwt-authentication-in-spring-boot-project/
@Component
public class JWTUtil
{
	@Value("${jwt.secret}")
	private String _secretKey;
	
	public String generateToken(String subject)
	{

		final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

		return Jwts.builder().setId("tk9931").setSubject(subject).setIssuer("ABC_Ltd").setAudience("XYZ_Ltd")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
				.signWith(secretKey, SignatureAlgorithm.HS512).compact();
	}

	// code to get Claims
	public Claims getClaims(String token) 
	{
		return (Claims) Jwts.parserBuilder().setSigningKey(Base64.getEncoder().encode(_secretKey.getBytes())).build()
				.parse(token).getBody();
	}

	// code to check if token is valid
	public boolean isValidToken(String token)
	{
		return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
	}

	// code to check if token is valid as per username
	public boolean isValidToken(String token, String username)
	{
		String tokenUserName = getSubject(token);
		return (username.equals(tokenUserName) && !isTokenExpired(token));
	}

	// code to check if token is expired
	public boolean isTokenExpired(String token)
	{
		return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
	}

	// code to get expiration date
	public Date getExpirationDate(String token) 
	{
		return getClaims(token).getExpiration();
	}

	// code to get expiration date
	public String getSubject(String token)
	{
		return getClaims(token).getSubject();
	}
}