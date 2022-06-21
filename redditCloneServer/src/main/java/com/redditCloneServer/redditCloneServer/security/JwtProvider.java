package com.redditCloneServer.redditCloneServer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Service
public class JwtProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY ;

    //final Key SECRET_KEY = secretKeyFor(SignatureAlgorithm.HS256,SECRET_KEY);

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(Authentication userDetails){
      //  System.out.print("userDetails.getName()"+userDetails.getName());
        Map<String,Object> claims = new HashMap<>();
       // System.out.println("claims"+claims);

        System.out.println(SECRET_KEY);
        return createToken(claims,userDetails.getName());
    }

    private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
            .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }

 //   public Boolean validateToken(String token,UserDetails userDetails){
 public Boolean validateToken(String token){
        final String username= extractUsername(token);
       // return (username.equalsIgnoreCase(userDetails.getUsername())&& !isTokenExpired(token));
        return  !isTokenExpired(token);
    }

}
