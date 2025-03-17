package br.com.foursales.ordermanagement.model.dto.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String identification;

    @NotEmpty
    private String password;

    private Set<RoleIdRequestDTO> roles;

}
