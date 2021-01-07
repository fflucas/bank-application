package fabio.zup.banco.config.security;

import fabio.zup.banco.model.UserApi;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtil {

    @Value("${banco.jwt.expiration}")
    private String expiration;
    @Value("${banco.jwt.secret}")
    private String secret;

    public String allocateToken(Authentication authentication){
        UserApi loggedUser = (UserApi) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("RESTful API bank")
                .setSubject(loggedUser.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }

    public String retrieveToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if (headerToken == null || headerToken.isEmpty() || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }
}
