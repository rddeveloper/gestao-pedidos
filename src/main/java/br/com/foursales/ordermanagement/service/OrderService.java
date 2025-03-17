package br.com.foursales.ordermanagement.service;


import br.com.foursales.ordermanagement.model.dto.order.request.OrderRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.OrderResponseDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.UserAverageTicketResponseDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.UserOrderSummaryResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderResponseDTO create(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO payOrder(Long orderId);
    List<UserOrderSummaryResponseDTO> getTop5UsersByTotalSpent();
    List<UserAverageTicketResponseDTO> getAverageTicketPerUser();
    BigDecimal getTotalRevenueForCurrentMonth();
}