package br.com.foursales.ordermanagement.model.dto.order.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;
}