package br.com.foursales.ordermanagement.controller.v1;


import br.com.foursales.ordermanagement.exception.auth.TokenRefreshException;
import br.com.foursales.ordermanagement.model.auth.RefreshToken;
import br.com.foursales.ordermanagement.model.dto.auth.request.AuthRequestDTO;
import br.com.foursales.ordermanagement.model.dto.auth.response.JwtResponseDTO;
import br.com.foursales.ordermanagement.model.dto.auth.request.RefreshTokenRequestDTO;
import br.com.foursales.ordermanagement.service.JwtService;
import br.com.foursales.ordermanagement.service.RefreshTokenService;
import br.com.foursales.ordermanagement.service.impl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Autenticação", description = "Endpoints para autenticação")
public class AuthController {

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService,
                          RefreshTokenService refreshTokenService,
                          UserDetailsServiceImpl userDetailsServiceImpl,
                          AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authRequestDTO.getUsername());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(userDetails))
                    .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }


    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userInfo.getUsername());
                    String accessToken = jwtService.generateToken(userDetails);
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new TokenRefreshException(refreshTokenRequestDTO.getToken(), "Refresh Token is not in DB..!!"));
    }

}
