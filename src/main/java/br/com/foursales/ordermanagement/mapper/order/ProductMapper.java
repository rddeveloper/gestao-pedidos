package br.com.foursales.ordermanagement.mapper.order;

import br.com.foursales.ordermanagement.model.order.Product;
import br.com.foursales.ordermanagement.model.dto.order.request.ProductRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDTO toResponseDTO(Product product);
    Product toEntity(ProductRequestDTO productRequestDTO);

    void updateEntityFromDTO(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
}