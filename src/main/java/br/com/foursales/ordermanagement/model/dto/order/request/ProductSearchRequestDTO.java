package br.com.foursales.ordermanagement.model.dto.order.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductSearchRequestDTO {

    private String name;

    private String category;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;
}