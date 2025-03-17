package br.com.foursales.ordermanagement.controller.v1;

import br.com.foursales.ordermanagement.model.dto.order.request.OrderRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.OrderResponseDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.UserAverageTicketResponseDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.UserOrderSummaryResponseDTO;
import br.com.foursales.ordermanagement.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Criar pedido", description = "Cria um novo pedido com múltiplos produtos.")
    @PreAuthorize("hasAuthority('CADASTRAR_PEDIDO')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid  @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO responseDTO = orderService.create(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Realizar pagamento", description = "Realiza o pagamento de um pedido e atualiza o estoque dos produtos.")
    @PostMapping("/{id}/payment")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable Long id) {
        OrderResponseDTO responseDTO = orderService.payOrder(id);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Top 5 usuários que mais compraram", description = "Retorna os 5 usuários que mais gastaram em compras.")
    @GetMapping("/top5-users")
    public ResponseEntity<List<UserOrderSummaryResponseDTO>> getTop5UsersByTotalSpent() {
        List<UserOrderSummaryResponseDTO> top5Users = orderService.getTop5UsersByTotalSpent();
        return ResponseEntity.ok(top5Users);
    }

    @Operation(summary = "Ticket médio por usuário", description = "Retorna o valor médio gasto por pedido para cada usuário.")
    @GetMapping("/average-ticket")
    public ResponseEntity<List<UserAverageTicketResponseDTO>> getAverageTicketPerUser() {
        List<UserAverageTicketResponseDTO> averageTickets = orderService.getAverageTicketPerUser();
        return ResponseEntity.ok(averageTickets);
    }

    @Operation(summary = "Valor total faturado no mês", description = "Retorna o valor total faturado no mês atual.")
    @GetMapping("/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenueForCurrentMonth() {
        BigDecimal totalRevenue = orderService.getTotalRevenueForCurrentMonth();
        return ResponseEntity.ok(totalRevenue);
    }

}