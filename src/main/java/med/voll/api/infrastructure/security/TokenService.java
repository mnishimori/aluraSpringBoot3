package med.voll.api.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String gerarToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("API voll.med")
          .withSubject(usuario.getLogin())
          .withExpiresAt(getDataExpiracao())
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro ao gerar o token JWT", exception);
    }
  }

  public String getSubject(String tokenJWT) {
    try {
      var algoritmo = Algorithm.HMAC256(this.secret);
      return JWT.require(algoritmo)
          .withIssuer("API voll.med")
          .build()
          .verify(tokenJWT)
          .getSubject();
    } catch (JWTVerificationException exception) {
      exception.printStackTrace();
      throw new RuntimeException("Token JWT inválido ou expirado!", exception);
    }
  }

  private Instant getDataExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
