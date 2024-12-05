package br.com.domingosdelazer.SistemaGestao.security.jwt;

import br.com.domingosdelazer.SistemaGestao.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Date;

@Service
public class JWTService {

    @Value("${security.jwt.signature-code}")
    private String signatureKey;

    public String generateToken(Usuario user) {
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toLocalDateTime();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validToken(String token) {
        try {
            Claims claims = this.getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime dateTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(dateTime);
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }

}
