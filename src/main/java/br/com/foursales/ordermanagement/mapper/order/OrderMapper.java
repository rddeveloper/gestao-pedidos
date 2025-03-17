package br.com.foursales.ordermanagement.mapper.order;

import br.com.foursales.ordermanagement.mapper.auth.UserMapper;
import br.com.foursales.ordermanagement.model.auth.UserInfo;
import br.com.foursales.ordermanagement.model.dto.order.request.OrderRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.OrderResponseDTO;
import br.com.foursales.ordermanagement.model.order.Order;
import br.com.foursales.ordermanagement.model.order.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ProductMapper.class, UserMapper.class, OrderProductMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDENTE")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "products", source = "orderProducts")
    @Mapping(target = "totalAmount", ignore = true)
    Order toEntity(OrderRequestDTO orderRequestDTO,
                   UserInfo user,
                   List<OrderProduct> orderProducts);

    @Mapping(target = "products", source = "products")
    OrderResponseDTO toResponseDTO(Order order);
}