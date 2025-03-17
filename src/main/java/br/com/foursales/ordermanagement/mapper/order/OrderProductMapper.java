package br.com.foursales.ordermanagement.mapper.order;

import br.com.foursales.ordermanagement.model.dto.order.OrderProductDTO;
import br.com.foursales.ordermanagement.model.order.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderProductMapper {

    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    @Mapping(target = "productId", source = "product.id")
    OrderProductDTO toDTO(OrderProduct orderProduct);

    @Mapping(target = "product.id", source = "productId")
    OrderProduct toEntity(OrderProductDTO orderProductDTO);
}