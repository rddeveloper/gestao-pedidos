package br.com.foursales.ordermanagement.model.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "data_expiracao")
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UserInfo userInfo;
}
