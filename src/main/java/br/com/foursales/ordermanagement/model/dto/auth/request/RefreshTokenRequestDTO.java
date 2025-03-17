package br.com.foursales.ordermanagement.model.dto.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestDTO {

    @NotEmpty
    private String token;

}
