package com.cignex.jwt;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cignex.entity.CustomUserDetails;
import com.cignex.entity.Role;
import com.cignex.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

@Component
public class JwtTokenUtil {

	private Clock clock = DefaultClock.INSTANCE;
	@Value("${jwt.signing.key.secret}")
	private String secret;
	@Value("${jwt.token.expiration.in.seconds}")
	private Long expiration;

	public String getUserNameFromToken(String token) {
		return getClaimsFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}

	private Boolean ignoreTokenExpiration(String token) {
		return false;
	}

	public String generateToken(CustomUserDetails userDetails) {
		Claims claims=Jwts.claims().setSubject(userDetails.getUsername());
		claims.put("id", userDetails.getUser().getId());
		claims.put("password", userDetails.getUser().getPassword());
		claims.put("role", userDetails.getUser().getRoles());
		return doGenerateToken(claims);
	}

	private String doGenerateToken(Claims claims) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);
		System.out.println(expirationDate);
		return Jwts.builder().setClaims(claims).setIssuedAt(createdDate).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}
	
	public String refreshToken(String token) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public User validate(String token) {
		User user=null;
		try {
			Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			user=new User();
			user.setName(claims.getSubject());
			user.setId((int) claims.get("id"));
			Set<Role> r=new HashSet<Role>(extracted(claims));
			System.out.println(claims.get("role"));
			user.setRoles(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	private Collection<? extends Role> extracted(Claims claims) {
		return (Collection<? extends Role>) claims.get("role");
	}
	
	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + expiration * 30);
	}
}
