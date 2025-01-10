package chat.global.security.jwt;

import chat.global.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Value("${jwt.accessToken-expiration-date}")
    private long tokenValidTime;

    @PostConstruct
    public void init() throws GeneralSecurityException, URISyntaxException, IOException {
        RsaUtil rsaUtil = new RsaUtil();
        privateKey = rsaUtil.getPrivateKey();
        publicKey = rsaUtil.getPublicKey();
    }

    public String createToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .claim("userPK", userDetails.getId())
                .issuer("PUSH_BROADCASTING")
                .expiration(getTokenExpiration(tokenValidTime))
                .issuedAt((Calendar.getInstance().getTime()))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        Long userId = claims.get("userPK", Long.class);
        CustomUserDetails customUserDetails = CustomUserDetails.of(userId);

        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(publicKey)
                    .build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    private Date getTokenExpiration(long expirationMillisecond) {
        Date date = new Date();
        return new Date(date.getTime() + expirationMillisecond);
    }
}
