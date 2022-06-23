package com.redditCloneServer.redditCloneServer.security;

import com.redditCloneServer.redditCloneServer.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Slf4j
public class JwtProvider {
//    @Value("${jwt.secret}")
//    private String SECRET_KEY ;
//
//    //final Key SECRET_KEY = secretKeyFor(SignatureAlgorithm.HS256,SECRET_KEY);
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(Authentication userDetails){
//      //  System.out.print("userDetails.getName()"+userDetails.getName());
//        Map<String,Object> claims = new HashMap<>();
//       // System.out.println("claims"+claims);
//
//        System.out.println(SECRET_KEY);
//        return createToken(claims,userDetails.getName());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//            .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
//            .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
//    }
//
// //   public Boolean validateToken(String token,UserDetails userDetails){
// public Boolean validateToken(String token){
//        final String username= extractUsername(token);
//       // return (username.equalsIgnoreCase(userDetails.getUsername())&& !isTokenExpired(token));
//        return  !isTokenExpired(token);
//    }


    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog1.jks");
            keyStore.load(resourceAsStream, "qwerty".toCharArray());
//            log.info("load the key"+keyStore);
//            log.info("private key "+keyStore.getKey("springblog1", "qwerty".toCharArray()));
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception occurred while loading keystore");
        }

    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        log.info("private key"+getPrivateKey());
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }


    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog1", "qwerty".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt){
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
    try{
        return keyStore.getCertificate("springblog1").getPublicKey();
    } catch (KeyStoreException e) {
        e.printStackTrace();
        throw new SpringRedditException("Exception occurred while retrieving public key from keystore");

    }
    }

    public String getUsernameFromJwt(String token){
        Claims claims= parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}
