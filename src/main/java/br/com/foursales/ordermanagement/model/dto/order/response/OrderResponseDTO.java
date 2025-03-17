package br.com.foursales.ordermanagement.model.dto.order.response;

import br.com.foursales.ordermanagement.model.dto.order.OrderProductDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;

    private String status;

    private BigDecimal totalAmount;

    private List<OrderProductDTO> products;
}