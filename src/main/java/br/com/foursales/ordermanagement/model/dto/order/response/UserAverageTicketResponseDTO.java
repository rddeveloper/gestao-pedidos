package br.com.foursales.ordermanagement.model.dto.order.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class UserAverageTicketResponseDTO {

    private Long userId;

    private String userName;

    private BigDecimal averageTicket;
}
