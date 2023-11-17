package nl.fontys.s3.huister.configuration.security.token.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.fontys.s3.huister.configuration.security.token.AccessToken;
import nl.fontys.s3.huister.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.huister.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.huister.configuration.security.token.exception.InvalidAccessTokenException;
import nl.fontys.s3.huister.persistence.entities.enumerator.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getRole()!=null) {
            claimsMap.put("role", accessToken.getRole());
        }
        if (accessToken.getId() != null) {
            claimsMap.put("id", accessToken.getId());
        }

        if (accessToken.getProfilePictureUrl()!=null){
            claimsMap.put("profilePictureUrl",accessToken.getProfilePictureUrl());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            //UserRole role = claims.get("role", UserRole.class);
            UserRole role=UserRole.valueOf(claims.get("role", String.class));
            Long id = claims.get("id", Long.class);
            String profilePictureUrl=claims.get("profilePictureUrl",String.class);

            return new AccessTokenImpl(claims.getSubject(), id, role, profilePictureUrl);
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}
