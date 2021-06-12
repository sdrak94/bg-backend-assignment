package bg.assignment.bg.backend.security.jwt;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import bg.assignment.bg.backend.rest.model.ValidRegistration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

//credits to: https://javatechonline.com/how-to-implement-jwt-authentication-in-spring-boot-project/
@Component
public class JWTUtil
{
	final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	public String generateToken(final ValidRegistration authenticatedRegistration)
	{
		return Jwts.builder()
				.claim("user_uuid", authenticatedRegistration.getColonistUUID().toString())
				.setSubject(authenticatedRegistration.getColonistId())
				.setId(UUID.randomUUID().toString())
				.setIssuer("bg.mars")
				.setAudience("bg.colonists")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
				.signWith(secretKey, SignatureAlgorithm.HS512).compact();
	}

	public Claims getClaims(String token) throws SignatureException
	{
		return (Claims) Jwts.parserBuilder().setSigningKey(secretKey).build().parse(token).getBody();
	}

	public boolean isValidToken(String token)
	{
		return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
	}

	public boolean isValidToken(String token, String username)
	{
		String tokenUserName = getSubject(token);
		return (username.equals(tokenUserName) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token)
	{
		return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
	}

	public Date getExpirationDate(String token) 
	{
		return getClaims(token).getExpiration();
	}

	public String getSubject(String token)
	{
		return getClaims(token).getSubject();
	}
}