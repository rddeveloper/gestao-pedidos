package br.com.foursales.ordermanagement.model.dto.auth.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleResponseDTO {

    private Long id;

    private String name;

    private String description;

    private List<PermissionsResponseDTO> permissions;

}
