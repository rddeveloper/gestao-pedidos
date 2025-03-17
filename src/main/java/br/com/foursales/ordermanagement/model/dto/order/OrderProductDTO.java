package br.com.foursales.ordermanagement.model.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDTO {


    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
