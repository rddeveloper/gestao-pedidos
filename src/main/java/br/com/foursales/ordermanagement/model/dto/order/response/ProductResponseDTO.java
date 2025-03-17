package br.com.foursales.ordermanagement.model.dto.order.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponseDTO {

    private Long id;

    private String name;

    private String category;

    private BigDecimal price;

    private Integer stock;
}