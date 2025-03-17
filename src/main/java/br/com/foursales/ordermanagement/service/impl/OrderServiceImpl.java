package br.com.foursales.ordermanagement.service.impl;

import br.com.foursales.ordermanagement.exception.BusinessException;
import br.com.foursales.ordermanagement.mapper.order.OrderMapper;
import br.com.foursales.ordermanagement.model.auth.UserInfo;
import br.com.foursales.ordermanagement.model.dto.order.request.OrderRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.OrderResponseDTO;
import br.com.foursales.ordermanagement.event.OrderEvent;
import br.com.foursales.ordermanagement.model.dto.order.response.UserAverageTicketResponseDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.UserOrderSummaryResponseDTO;
import br.com.foursales.ordermanagement.model.order.Order;
import br.com.foursales.ordermanagement.model.order.OrderProduct;
import br.com.foursales.ordermanagement.model.order.Product;
import br.com.foursales.ordermanagement.repository.jpa.OrderRepository;
import br.com.foursales.ordermanagement.repository.elasticsearch.ProductElasticsearchRepository;
import br.com.foursales.ordermanagement.service.OrderService;
import br.com.foursales.ordermanagement.service.ProductService;
import br.com.foursales.ordermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_NOT_FOUND = "Pedido não encontrado";
    public static final String ORDER_ALREADY_PAYMENT = "Pedido já está pago";
    public static final String ORDER_NOT_PAYMENT = "Pedido está cancelado e não pode ser pago";

    private final ProductService productService;

    private final UserService userService;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();

        log.info("Criando pedido para o usuário: {}", userDetail.getUsername());

        UserInfo user = userService.getUserInfo(userDetail.getUsername());

        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        List<OrderProduct> orderProducts = orderRequestDTO.getProducts().stream()
                .map(orderProductDTO -> {

                    Product product = productService.getProduct(orderProductDTO.getProductId());

                    if (product.getStock() < orderProductDTO.getQuantity()) {
                        throw new BusinessException("Produto " + product.getName() + " sem estoque suficiente");
                    }

                    BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(orderProductDTO.getQuantity()));

                    totalAmount[0] = totalAmount[0].add(subtotal);

                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(orderProductDTO.getQuantity());
                    return orderProduct;
                })
                .collect(Collectors.toList());

        Order order = orderMapper.toEntity(orderRequestDTO, user, orderProducts);
        order.setTotalAmount(totalAmount[0]);

        orderProducts.forEach(orderProduct -> orderProduct.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        kafkaTemplate.send("order.created", new OrderEvent(savedOrder.getId(), "CRIADO"));

        log.info("Pedido criado com sucesso: {}", savedOrder.getId());

        return orderMapper.toResponseDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDTO payOrder(Long orderId) {
        log.info("Realizando pagamento do pedido: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND));

        if ("PAGO".equals(order.getStatus())) {
            throw new BusinessException(ORDER_ALREADY_PAYMENT);
        }
        if ("CANCELADO".equals(order.getStatus())) {
            throw new BusinessException(ORDER_NOT_PAYMENT);
        }

        order.setStatus("PAGO");
        Order paidOrder = orderRepository.save(order);

        kafkaTemplate.send("order.paid", new OrderEvent(paidOrder.getId(), "PAGO"));

        log.info("Pagamento realizado com sucesso para o pedido: {}", orderId);

        return orderMapper.toResponseDTO(paidOrder);
    }


    @Override
    public List<UserOrderSummaryResponseDTO> getTop5UsersByTotalSpent() {
        log.info("Consultando top 5 usuários que mais compraram");

        List<Object[]> results = orderRepository.findTop5UsersByTotalSpent();

        return results.stream()
                .map(result -> new UserOrderSummaryResponseDTO(
                        (Long) result[0],
                        (String) result[1],
                        (BigDecimal) result[2]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAverageTicketResponseDTO> getAverageTicketPerUser() {
        log.info("Consultando ticket médio dos pedidos de cada usuário");

        List<Object[]> results = orderRepository.findAverageTicketPerUser();

        return results.stream()
                .map(result -> new UserAverageTicketResponseDTO(
                        (Long) result[0],
                        (String) result[1],
                        (BigDecimal) result[2]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getTotalRevenueForCurrentMonth() {
        log.info("Consultando valor total faturado no mês");

        return orderRepository.findTotalRevenueForCurrentMonth();
    }


}