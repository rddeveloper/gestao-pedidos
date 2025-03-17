package br.com.foursales.ordermanagement.model.dto.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordUpdateRequestDTO {

    @NotEmpty
    private String currentPassword;

    @NotEmpty
    private String newPassword;
}