package br.com.foursales.ordermanagement.model.dto.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateRequestDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private Set<RoleIdRequestDTO> roles;
}