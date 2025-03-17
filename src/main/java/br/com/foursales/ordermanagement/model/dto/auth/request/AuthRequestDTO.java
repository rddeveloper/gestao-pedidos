package br.com.foursales.ordermanagement.model.dto.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
