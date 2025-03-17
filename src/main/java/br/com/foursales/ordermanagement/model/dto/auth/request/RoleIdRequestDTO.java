package br.com.foursales.ordermanagement.model.dto.auth.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleIdRequestDTO {

    @NotEmpty
    private Long id;

}
