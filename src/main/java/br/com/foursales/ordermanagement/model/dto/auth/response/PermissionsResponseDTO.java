package br.com.foursales.ordermanagement.model.dto.auth.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PermissionsResponseDTO {

    private Long id;

    private String name;

    private String description;

}
