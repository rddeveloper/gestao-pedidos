package br.com.foursales.ordermanagement.model.dto.auth.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDTO {

    private Long id;

    private String username;

    private String name;

    private String email;

    private List<RoleResponseDTO> roles;
}
