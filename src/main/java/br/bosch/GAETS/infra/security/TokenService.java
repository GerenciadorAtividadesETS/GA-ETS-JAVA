package br.bosch.GAETS.infra.security;

import br.bosch.GAETS.model.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.*;

@Service
public class TokenService {
    // CHAVE DE SEGURANÇA API
    @Value("{api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(usuario.getEdv())
                    .withIssuer("API")
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        }
        catch (JWTCreationException exception) {
            throw new RuntimeException("Falha ao gerar TOKEN");
        }
    }

    // TOKEN EXPIRA EM 2 HORAS
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    // RETORNAR SUBJECT(EDV) DO TOKEN
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }
        catch (JWTVerificationException exception) {
            throw new RuntimeException("TOKEN inválido");
        }
    }
}