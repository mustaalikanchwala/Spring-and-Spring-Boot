package com.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.Decoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class jwtUtils {
        private String jwtSecret = "thejwtseccereteforjwtdemoprojectandlearning";
        private int expiresInMils = 172800000;

        public String getJwtFromHeader(HttpServletRequest request){
            String jwt = request.getHeader("Authorization");
            if(jwt == null && !jwt.startsWith("Bearer ")) return null;
            return jwt.substring(7);
        }
        public String generateJwtfromUsername(UserDetails userDetails){
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime()+expiresInMils))
                    .signWith(key())
                    .compact();
        }
        public boolean validateJwt(String jwt){
            try{
                Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwt);
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }

        public Key key(){
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        }

    public String getUserNamefromToken(String jwt) {
            return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    public Claims getAllClaims(String jwt) {
            return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwt).getPayload();
    }
}
