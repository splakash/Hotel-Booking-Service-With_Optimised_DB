package app.HotelManagement.UserManagement;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {



    @Value("${jwt.secret}")
    private String secret ;

    @Value("${jwt.expiration}")
    private long expiration;


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String Username, String role) {
        return Jwts.builder()
                .setSubject(Username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public String extractUsername(String jwtToken) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(getSigningKey())
//                    .build()
//                    .parseClaimsJws(jwtToken)
//                    .getBody();
//
//            return claims.getSubject();
//
//        } catch (SignatureException e) {
//            throw new RuntimeException("Invalid JWT signature");
//        }
//    }

//    public boolean isTokenValid(String jwtToken, UserDetails userDetails){
//        final String username = extractUsername(jwtToken);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
//    }

//    public boolean isTokenExpired(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())   // consistent with extractUsername
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getExpiration().before(new Date());
//    }
    // CLEANER VERSION — single parse, no duplication
        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);   // one parse
            String username = claims.getSubject();
            Date expiry = claims.getExpiration();
            return username.equals(userDetails.getUsername())
                    && expiry.after(new Date());
        } catch (JwtException e) {
            return false;   // expired, tampered, wrong signature — all invalid
        }
    }
}
