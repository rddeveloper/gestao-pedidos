package br.com.foursales.ordermanagement.service;


import br.com.foursales.ordermanagement.exception.auth.TokenRefreshException;
import br.com.foursales.ordermanagement.model.auth.RefreshToken;
import br.com.foursales.ordermanagement.repository.jpa.RefreshTokenRepository;
import br.com.foursales.ordermanagement.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${order-management-security.app.refresh.token.expiration}")
    private Long jwtExpiration;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(jwtExpiration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), " Refresh token is expired. Please make a new login..!");
        }
        return token;

    }

}
