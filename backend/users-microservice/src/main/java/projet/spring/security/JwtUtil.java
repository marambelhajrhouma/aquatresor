package projet.spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import projet.spring.entities.User;

import java.util.Date;

@Component
public class JwtUtil {

    // Utilisation des constantes de SecParams
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SecParams.SECRET);
    private static final long EXPIRATION_TIME = SecParams.EXP_TIME;

    // Générer un token JWT pour un User
    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(ALGORITHM);
    }

    // Extraire le nom d'utilisateur du token
    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);
        return decodedJWT.getSubject();
    }

    // Extraire la date d'expiration du token
    public Date extractExpiration(String token) {
        DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);
        return decodedJWT.getExpiresAt();
    }

    // Valider le token
    public Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    // Vérifier si le token est expiré
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}