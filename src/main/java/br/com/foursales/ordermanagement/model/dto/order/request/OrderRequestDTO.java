package br.com.foursales.ordermanagement.model.dto.order.request;

import br.com.foursales.ordermanagement.model.dto.order.OrderProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    @Valid
    private List<OrderProductDTO> products;
}