package br.com.foursales.ordermanagement.consumer;

import br.com.foursales.ordermanagement.event.OrderEvent;
import br.com.foursales.ordermanagement.exception.BusinessException;
import br.com.foursales.ordermanagement.model.order.Order;
import br.com.foursales.ordermanagement.model.order.Product;
import br.com.foursales.ordermanagement.repository.elasticsearch.ProductElasticsearchRepository;
import br.com.foursales.ordermanagement.repository.jpa.OrderRepository;
import br.com.foursales.ordermanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderPaidConsumer {

    public static final String ORDER_NOT_FOUND = "Pedido não encontrado";

    private final ProductService productService;

    private final OrderRepository orderRepository;

    private final ProductElasticsearchRepository productElasticsearchRepository;

    @Transactional
    @KafkaListener(topics = "order.paid", groupId = "gestao-pedidos-group")
    public void consumeOrderPaid(OrderEvent orderEvent) {
        log.info("Recebido evento order.paid para o pedido: {}", orderEvent.getOrderId());

        Order order = orderRepository.findById(orderEvent.getOrderId())
                .orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND));

        order.getProducts().forEach(orderProduct ->
                productService.updateStock(orderProduct.getProduct().getId(), orderProduct.getQuantity()));

        log.info("Dados de estoque atualizados no elastisearch");

    }
}